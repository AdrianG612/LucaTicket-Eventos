package com.ejemplos.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.EventoAdapter;
import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;


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
	

	@Operation(
		summary = "Dar de alta un nuevo evento",
		description = "Permite crear un nuevo evento en la base de datos. Ignora el Id_evento si se especifica en el Json de entrada."
	)
	@PostMapping()
	public ResponseEntity<EventoResponse> saveEvento(@RequestBody @Valid EventoResponse input) {

		//Se ignora el id si se envía
	    if (input.getId_evento() != null) {
	        input.setId_evento(null);
	    }

	    // Se guarda el Evento covertido a Entidad
	    EventoResponse res = eventoAdapter.of(eventoService.saveEvento(eventoAdapter.of(input)).get());

	    //Se construye la ResponseEntity con el código 201
	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(res);

	}
	
	@Operation(
		summary = "Listar eventos almacenados",
		description = "Permite obtener un listado de todos los elementos almacenados en la base de datos."
	)
	@GetMapping()
	public ResponseEntity<List<EventoResponse>> obtenerEventos() {
		List<EventoResponse> eventos = eventoAdapter.of(eventoService.findAll());
		
		//Si la lista está vacía devuelve un response entity que lo indique
		if (eventos.isEmpty()) {
			logger.warn("No se encontraron eventos en la base de datos.");
			return ResponseEntity.noContent().build();
		}
		
		//Se construye la ResponseEntity con el código 200
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(eventos);
	}

}
