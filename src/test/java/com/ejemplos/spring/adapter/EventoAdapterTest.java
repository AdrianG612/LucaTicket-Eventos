package com.ejemplos.spring.adapter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;

class EventoAdapterTest {

	@Test
	void shouldNombreEventoResponseIgualANombreEvento() {
		
		EventoAdapter adapter = new EventoAdapter();
		
		Evento evento = new Evento();
		
        evento.setNombre("Prueba");
        evento.setDescripcion("Concierto de prueba");
        evento.setFecha_evento(LocalDate.of(2024, 12, 15));
        evento.setHora_evento(LocalTime.of(20, 0));
        evento.setPrecio_minimo(50.0);
        evento.setPrecio_maximo(200.0);
        evento.setLocalidad("Madrid");
        evento.setGenero("Rock");
        evento.setNombre_recinto("Metropolitano");
        
        EventoResponse eventoResponse = adapter.of(evento);
        
        assertEquals(evento.getNombre(), eventoResponse.getNombre());
	}
	
	@Test
	void shouldNombreEventoIgualANombreEventoResponse() {
		
		EventoAdapter adapter = new EventoAdapter();
		
		EventoResponse eventoResponse = new EventoResponse();
		
        eventoResponse.setNombre("Prueba");
        eventoResponse.setDescripcion("Concierto de prueba");
        eventoResponse.setFecha_evento(LocalDate.of(2024, 12, 15));
        eventoResponse.setHora_evento(LocalTime.of(20, 0));
        eventoResponse.setPrecio_minimo(50.0);
        eventoResponse.setPrecio_maximo(200.0);
        eventoResponse.setLocalidad("Madrid");
        eventoResponse.setGenero("Rock");
        eventoResponse.setNombre_recinto("Metropolitano");
        
        Evento evento = adapter.of(eventoResponse);
        
        assertEquals(eventoResponse.getNombre(), evento.getNombre());
	}

}
