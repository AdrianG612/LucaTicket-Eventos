package com.ejemplos.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.EventoAdapter;
import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	private static final Logger logger = LoggerFactory.getLogger(EventoController.class);

	@Autowired
	EventoService eventoService;

	@Autowired
	EventoAdapter eventoAdapter;

	/**
	 * Crear un nuevo evento en la base de datos.
	 * 
	 * @param input Objeto EventoResponse con los datos del evento que se desea
	 *              crear.
	 * @return ResponseEntity con el evento creado si se guarda correctamente, o un
	 *         mensaje de error si falla.
	 */
	@Operation(summary = "Dar de alta un nuevo evento", description = "Permite crear un nuevo evento en la base de datos. Se ignora el ID si se especifica en la entrada JSON.")
	@PostMapping()
	public ResponseEntity<?> saveEvento(@RequestBody @Valid EventoResponse input) {

		// Se ignora el ID si se envía
		if (input.getId_evento() != null) {
			input.setId_evento(null);
		}

		// Se guarda el Evento convertido a Entidad
		Optional<Evento> res = eventoService.saveEvento(eventoAdapter.of(input));

		// Validar si el evento fue guardado
		if (res.isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("message",
					"No se pudo guardar ya que no se puede introducir un precio_minimo mayor o igual al precio_maximo y viceversa");
			response.put("input", input);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Construir la ResponseEntity con el código 201 y el cuerpo del evento creado
		return ResponseEntity.status(HttpStatus.CREATED) // Código HTTP 201
				.body(res.get()); // El evento guardado como cuerpo de la respuesta
	}

	/**
	 * Obtener una lista de todos los eventos almacenados en la base de datos.
	 * 
	 * @return ResponseEntity con una lista de objetos EventoResponse si hay eventos
	 *         en la base de datos, o un código 204 si no hay datos.
	 */
	@Operation(summary = "Listar eventos almacenados", description = "Obtiene una lista de todos los eventos registrados en la base de datos.")
	@GetMapping()
	public ResponseEntity<List<EventoResponse>> obtenerEventos() {
		List<EventoResponse> eventos = eventoAdapter.of(eventoService.findAll());

		// Si la lista está vacía devuelve un response entity que lo indique
		if (eventos.isEmpty()) {
			logger.warn("No se encontraron eventos en la base de datos.");
			return ResponseEntity.noContent().build();
		}

		// Se construye la ResponseEntity con el código 200
		return ResponseEntity.status(HttpStatus.OK).body(eventos);
	}

	/**
	 * Buscar un evento específico por su ID.
	 * 
	 * @param id Identificador único del evento a buscar.
	 * @return ResponseEntity con el evento encontrado, o un mensaje de error si el
	 *         ID no existe en la base de datos.
	 */
	@Operation(summary = "Buscar evento por ID", description = "Permite obtener un evento específico utilizando su identificador único.")
	@GetMapping("/{id}")
	public ResponseEntity<?> getEvento(@PathVariable Long id) {

		Optional<Evento> result = eventoService.findBydId(id);

		if (!result.isPresent()) {

			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("message", "El evento con el id: " + id + " enviado no se encuentra en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.status(HttpStatus.OK).body(eventoAdapter.of(result.get()));

	}

	/**
	 * Listar eventos que coincidan con un nombre dado.
	 * 
	 * @param nombre Nombre por el cual buscar los eventos.
	 * @return ResponseEntity con una lista de eventos que coincidan con el nombre,
	 *         o un código 404 si no se encuentran resultados.
	 */
	@Operation(summary = "Listar eventos por nombre", description = "Obtiene todos los eventos que coincidan con un nombre específico.")
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<?> getEvento(@PathVariable String nombre) {
		List<Evento> eventos = eventoService.findByNombre(nombre);

		// Si no se encuentra ningún evento
		if (eventos.isEmpty()) {
			logger.warn("No se encontraron eventos con el nombre: {}", nombre);
			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("message", "No existe ningún evento que coincida con el nombre: " + nombre);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		logger.info("Se encontraron {} evento(s) con el nombre: {}", eventos.size(), nombre);

		// Mapear eventos a EventoResponse
		List<EventoResponse> eventosResponse = eventos.stream().map(eventoAdapter::of).toList();

		return ResponseEntity.ok(eventosResponse);
	}

	/**
	 * Eliminar un evento utilizando su ID.
	 * 
	 * @param id Identificador único del evento a eliminar.
	 * @return ResponseEntity con los detalles del evento eliminado si existe, o un
	 *         mensaje de error si no se encuentra.
	 */
	@Operation(summary = "Eliminar un evento", description = "Permite eliminar un evento de la base de datos utilizando su identificador único.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEvento(@PathVariable Long id) {

		// Intentar eliminar el evento
		Optional<Evento> eventoOptional = eventoService.deleteEvento(id);

		if (eventoOptional.isPresent()) {
			// logger.info("Evento con ID {} eliminado exitosamente.", id);

			// Convertir el evento eliminado a EventoResponse
			EventoAdapter adapter = new EventoAdapter();
			EventoResponse response = adapter.of(eventoOptional.get());

			return ResponseEntity.ok(response); // Devolver el evento eliminado
		} else {
			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("message",
					"El evento con el id: " + id + " no se encuentra en la base de datos para ser eliminado");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

		}
	}

}
