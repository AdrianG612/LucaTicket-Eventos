package com.ejemplos.spring.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;


import org.springframework.stereotype.Component;

@Component
public class EventosAdapter {
	
	public EventoResponse of(Evento evento) {
		
		EventoResponse dto = new EventoResponse();
		
		dto.setNombre(evento.getNombre());
		dto.setDescripcion(evento.getDescripcion());
		dto.setFechaEvento(evento.getFechaEvento());
		dto.setHora_evento(evento.getHora_evento());
		dto.setPrecio_minimo(evento.getPrecio_minimo());
		dto.setPrecio_maximo(evento.getPrecio_maximo());
		dto.setLocalidad(evento.getLocalidad());
		dto.setGenero(evento.getGenero());
		dto.setNombre_recinto(evento.getNombre_recinto());
	}
	
	
	public List<EventoResponse> of(List<Evento> eventos){
		
		List<EventoResponse> response = new ArrayList<>();
		
		for(Evento e: eventos)
			response.add(this.of(e));
		
		return response;
		
		
	}
	
	
	

}
