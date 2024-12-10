package com.ejemplos.spring.service;

import java.util.Optional;


import com.ejemplos.spring.model.Evento;

public interface EventoService {
	
	public Optional<Evento> saveEvento(Evento evento);
}
