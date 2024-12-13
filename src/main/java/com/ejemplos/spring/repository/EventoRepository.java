package com.ejemplos.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplos.spring.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findByNombre(String nombre);
	
}
