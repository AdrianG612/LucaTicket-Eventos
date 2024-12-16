package com.ejemplos.spring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ejemplos.spring.model.Evento;

@DataJpaTest
public class EventoRepositoryTest {
	
	@Autowired
    private EventoRepository eventoRepository;
	
	@Test
    void shouldBaseEstaLevantada() {
        // Crear un nuevo Evento
        Evento evento = new Evento();
        evento.setNombre("Concierto de Prueba");

        // Guardar el Evento
        Evento savedEvento = eventoRepository.save(evento);

        // Verificar que fue guardado correctamente
        assertThat(savedEvento.getId_evento()).isNotNull();

        // Verificar que el repositorio puede recuperar el Evento
        Evento retrievedEvento = eventoRepository.findById(savedEvento.getId_evento()).orElse(null);
        assertThat(retrievedEvento).isNotNull();
        assertThat(retrievedEvento.getNombre()).isEqualTo("Concierto de Prueba");
    }
}
