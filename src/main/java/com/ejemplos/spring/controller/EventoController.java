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
	 * Crear Endpoint @PostMapping(“/eventos”) saveEvento(@RequestBody
	 * EventoResponse eventoResponse):
	 * 
	 * @param input
	 * @return ResponseEntity <EventoResponse>
	 * 
	 *         FALTA IMPLEMENTAR RESPONSE ENTITY
	 */

	@Operation(summary = "Dar de alta un nuevo evento", description = "Permite crear un nuevo evento en la base de datos. Ignora el Id_evento si se especifica en el Json de entrada.")
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

	@Operation(summary = "Listar eventos almacenados", description = "Permite obtener un listado de todos los elementos almacenados en la base de datos.")
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
	 * Crear Endpoint @GetMapping(“/{id}”) getEvento (@PathVariable Long id),
	 * devuelve ResponseEntity<EventoResponse>
	 */

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvento(@PathVariable Long id) {

		Optional<Evento> result = eventoService.findBydId(id);

		if (!result.isPresent()) {

			Map<String, Object> response = new HashMap<>();
			response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
			response.put("timestamp", System.currentTimeMillis());
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("message", "El usuario con el id: " + id + " enviado no se encuentra en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.status(HttpStatus.OK).body(eventoAdapter.of(result.get()));

	}

	@Operation(summary = "Listar eventos por nombre", description = "Permite obtener un listado de todos los eventos con el mismo nombre.")
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<List<EventoResponse>> getEvento(@PathVariable String nombre) {
		List<Evento> eventos = eventoService.findByNombre(nombre);

		// Si no se encuentra ningún evento
		if (eventos.isEmpty()) {
			logger.warn("No se encontraron eventos con el nombre: {}", nombre);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Se encontraron {} evento(s) con el nombre: {}", eventos.size(), nombre);

		// Mapear eventos a EventoResponse
		List<EventoResponse> eventosResponse = eventos.stream().map(eventoAdapter::of).toList();

		return ResponseEntity.ok(eventosResponse);
	}

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
					"El usuario con el id: " + id + " no se encuentra en la base de datos para ser eliminado");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

		}
	}

}
