package com.ejemplos.spring;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.spring.repository.EventoRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class LucaTicketEventosApplicationTests {

	@Autowired
    private EventoRepository eventoRepository;

    @Test
    void whenDatabaseIsAvailable_thenRepositoryShouldWork() {
    	//Comprobar que el repositorio no es null
        assertNotNull(eventoRepository, "El repositorio no debería ser nulo");

        // Ejecutar una operación simple para verificar la conexión
        long count = eventoRepository.count();  //Cuenta los registros que hay en la base de datos 
        assertTrue(count >= 0, "La base de datos debería devolver un conteo válido");
    }

}
