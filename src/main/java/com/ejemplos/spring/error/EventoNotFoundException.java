package com.ejemplos.spring.error;

public class EventoNotFoundException extends RuntimeException {

	public EventoNotFoundException() {
		super("Evento no encontrado");
	}

}
