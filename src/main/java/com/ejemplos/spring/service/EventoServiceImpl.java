package com.ejemplos.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.repository.EventoRepository;
import com.ejemplos.spring.response.EventoResponse;
import java.util.Optional;
@Service
public class EventoServiceImpl implements EventoService {
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Override
	public Optional<Evento> saveEvento(Evento evento) {
		return Optional.of(eventoRepository.save(evento));
	}

	@Override
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	@Override
	public Optional<Evento> findBydId(Long id) {

		return eventoRepository.findById(id);
		
	}

}
