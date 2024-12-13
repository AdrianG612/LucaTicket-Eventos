package com.ejemplos.spring.service;

import java.util.List;
import java.util.Optional;


import com.ejemplos.spring.model.Evento;

public interface EventoService {
	
	public Optional<Evento> saveEvento(Evento evento);
	
	public List<Evento> findAll();

	public Optional<Evento> findBydId(Long id);
	
	public List<Evento> findByNombre(String nombre);
}
