package com.ejemplos.spring.controller;

import java.util.Optional;

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

@RestController
@RequestMapping("/eventos")
public class EventosController {
	
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
	public EventoResponse saveEvento(@RequestBody EventoResponse input){
		
		Evento e = eventoAdapter.of(input);
		
		Optional<Evento> res = eventoService.saveEvento(e);
		
		return eventoAdapter.of(res.get());
		
	}
	

}
