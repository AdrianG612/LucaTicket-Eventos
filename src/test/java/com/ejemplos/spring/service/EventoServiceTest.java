package com.ejemplos.spring.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.repository.EventoRepository;

@SpringBootTest
public class EventoServiceTest {
	
	@Mock
    private EventoRepository eventoRepository; // Mock del repositorio

    @InjectMocks
    private EventoServiceImpl eventoService; // El servicio que estamos probando

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba
    }
    
	@Test
    public void testFindAll() {
 
        Evento evento1 = new Evento(1L, "Concierto 1", "Descripción 1", LocalDate.of(2024, 12, 15), LocalTime.of(19, 30), 20.0, 50.0, "Madrid", "Pop", "Recinto A");
        Evento evento2 = new Evento(2L, "Concierto 2", "Descripción 2", LocalDate.of(2024, 12, 20), LocalTime.of(21, 0), 25.0, 60.0, "Barcelona", "Rock", "Recinto B");
        
        // Configurar el comportamiento del mock para devolver una lista de eventos
        when(eventoRepository.findAll()).thenReturn(Arrays.asList(evento1, evento2));

        List<Evento> eventos = eventoService.findAll();

        assertNotNull(eventos); // comprobamos que la lista devuelta no es nula
        assertEquals(2, eventos.size());  //deberia ser 2

        //verificar eventos
        assertEquals("Concierto 1", eventos.get(0).getNombre());
        assertEquals("Concierto 2", eventos.get(1).getNombre());

        //verificar findAll del repo
        verify(eventoRepository, times(1)).findAll();
    }
	
}
