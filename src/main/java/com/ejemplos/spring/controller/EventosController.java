package com.ejemplos.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.adapter.EventoAdapter;
import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoService;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoService;
 

@RestController
@RequestMapping("/eventos")
public class EventosController {
	
    private static final Logger logger = LoggerFactory.getLogger(EventosController.class);
	
	@Autowired
	EventoService eventoService;
	
	@Autowired 
	EventoAdapter eventoAdapter;
	
	/**
	 * Crear Endpoint  @PostMapping(“/eventos”) saveEvento(@RequestBody EventoResponse eventoResponse): 
	 * @param input
	 * @return ResponseEntity <EventoResponse>
	 * 
	 * FALTA IMPLEMENTAR RESPONSE ENTITY
	 */
	
	@PostMapping()	
	public ResponseEntity<Evento> saveEvento(@Valid @RequestBody EventoResponse eventoResponse){
		
		if (eventoResponse.getId_evento()!= null) {
			eventoResponse.setId_evento(null);
		}
		
		Evento evento = eventoService.saveEvento(eventoAdapter.of(eventoResponse)).get();
        return ResponseEntity.ok(evento);
		
	}
	


	
	
	@GetMapping("/eventos")
    public ResponseEntity<List<EventoResponse>> obtenerEventos() {
		List<EventoResponse> eventos = eventoAdapter.of(eventoService.findAll());
		
        if (eventos.isEmpty()) {
        	logger.warn("No se encontraron eventos en la base de datos.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }
	
 
}

