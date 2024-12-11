package com.ejemplos.spring.controller;
 
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
    private EventoService eventoService;
	
	@GetMapping("/eventos")
    public ResponseEntity<List<EventoResponse>> obtenerEventos() {
		List<EventoResponse> eventos = eventoService.findAll();
        if (eventos.isEmpty()) {
        	logger.warn("No se encontraron eventos en la base de datos.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }
	
 
}