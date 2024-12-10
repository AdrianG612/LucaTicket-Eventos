package com.ejemplos.spring.adapter;

import java.util.ArrayList;
import java.util.List;
import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;

import org.springframework.stereotype.Component;

@Component
public class EventoAdapter {

	public EventoResponse of(Evento evento) {

		EventoResponse dto = new EventoResponse();
		
		dto.setId_evento(evento.getId_evento());
		dto.setNombre(evento.getNombre());
		dto.setDescripcion(evento.getDescripcion());
		dto.setFecha_evento(evento.getFecha_evento());
		dto.setHora_evento(evento.getHora_evento());
		dto.setPrecio_minimo(evento.getPrecio_minimo());
		dto.setPrecio_maximo(evento.getPrecio_maximo());
		dto.setLocalidad(evento.getLocalidad());
		dto.setGenero(evento.getGenero());
		dto.setNombre_recinto(evento.getNombre_recinto());

		return dto;
	}

	public List<EventoResponse> of(List<Evento> eventos) {

		List<EventoResponse> response = new ArrayList<>();

		for (Evento e : eventos)
			response.add(this.of(e));

		return response;
	}
	
	public Evento of(EventoResponse eventoResponse) {

		Evento evento = new Evento();
		
		evento.setId_evento(eventoResponse.getId_evento());
		evento.setNombre(eventoResponse.getNombre());
		evento.setDescripcion(eventoResponse.getDescripcion());
		evento.setFecha_evento(eventoResponse.getFecha_evento());
		evento.setHora_evento(eventoResponse.getHora_evento());
		evento.setPrecio_minimo(eventoResponse.getPrecio_minimo());
		evento.setPrecio_maximo(eventoResponse.getPrecio_maximo());
		evento.setLocalidad(eventoResponse.getLocalidad());
		evento.setGenero(eventoResponse.getGenero());
		evento.setNombre_recinto(eventoResponse.getNombre_recinto());

		return evento;
	}

	public List<Evento> ofEvento(List<EventoResponse> responses) {
		List<Evento> eventos = new ArrayList<>();
		
		for (EventoResponse e : responses) {
			eventos.add(this.of(e));
		}
		
		return eventos;
	}
}
