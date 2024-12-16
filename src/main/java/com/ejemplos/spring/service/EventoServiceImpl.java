package com.ejemplos.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.repository.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService {
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Override
	public Optional<Evento> saveEvento(Evento evento) {
	    // Validar el evento antes de intentar guardarlo
	    if (comprobacion(evento)) {
	        Evento savedEvento = eventoRepository.save(evento); // Guardar el evento y devolverlo
	        return Optional.of(savedEvento); // Devolver el evento envuelto en un Optional
	    }
	    
	    // Si no pasa la validación, devolver un Optional vacío
	    return Optional.empty();
	}
	
	
	public boolean comprobacion(Evento evento) {
	    boolean devolver = true;

	    // Validar que el precio mínimo no sea mayor o igual al máximo
	    if (evento.getPrecio_minimo() >= evento.getPrecio_maximo()) {
	        devolver = false;
	    }

	    // Validar que el precio máximo no sea menor o igual al mínimo
	    if (evento.getPrecio_maximo() <= evento.getPrecio_minimo()) {
	        devolver = false;
	    }

	    return devolver;
	}

	@Override
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	@Override
	public Optional<Evento> findBydId(Long id) {

		return eventoRepository.findById(id);
		
	}

	@Override
	public List<Evento> findByNombre(String nombre) {
		return eventoRepository.findByNombre(nombre);
	}

	@Override
	public Optional<Evento> deleteEvento(Long id) {
	    Optional<Evento> eventoDelete= eventoRepository.findById(id);

	    if (eventoDelete.isPresent()) {
	        eventoRepository.delete(eventoDelete.get());
	        return eventoDelete; 
	    }

	    return Optional.empty(); // Si no lo encuentra devolver vacío
	}

}
