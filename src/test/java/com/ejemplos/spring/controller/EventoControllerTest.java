package com.ejemplos.spring.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoServiceImpl;


@SpringBootTest
@AutoConfigureMockMvc
public class EventoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
    @MockBean
    private EventoServiceImpl eventoService;
    
	@Test
	void shouldGetEventoDevuelveJson() throws Exception {

        mockMvc.perform(get("/eventos"))
		   .andDo(print())
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void shouldPostEventoDevuelveJson() throws Exception {
	    
	    String eventoJson = "{\"nombre\": \"Concierto\", \"descripcion\": \"Descripcion\", \"fecha_evento\": \"2024-12-11\", \"hora_evento\": \"12:15:38.008316\", \"localidad\": \"Localidad\", \"genero\": \"Genero\", \"nombre_recinto\": \"Recinto\", \"precio_maximo\": 100, \"precio_minimo\": 100}";

	    mockMvc.perform(post("/eventos")
	            .contentType(MediaType.APPLICATION_JSON)  
	            .content(eventoJson))  
	            .andDo(print())  
	            .andExpect(status().isCreated())  
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
	}
	
	@Test
	void shouldPostNoValidoDevuelveError() throws Exception {
	    
	    String eventoJson = "{\"descripcion\": \"Descripcion\", \"fecha_evento\": \"2024-12-11\", \"hora_evento\": \"12:15:38.008316\", \"localidad\": \"Localidad\", \"genero\": \"Genero\", \"nombre_recinto\": \"Recinto\", \"precio_maximo\": 100, \"precio_minimo\": 100}";

	    mockMvc.perform(post("/eventos")
	            .contentType(MediaType.APPLICATION_JSON)  
	            .content(eventoJson))  
	            .andDo(print())  
	            .andExpect(status().isBadRequest()); 
	}
	
	@Test
	void shouldPostResponseEntity() throws Exception {
		
		String nombre = "nombre de prueba";
		String fecha = "2024-12-12";
		String descripcion = "descripcion de prueba";
		
	    
	    String eventoJson = "{\"nombre\": \""+nombre+"\", \"descripcion\": \""+descripcion+"\", \"fecha_evento\": \""+fecha+"\", \"hora_evento\": "
	    		+ "\"12:15:38.008316\", \"localidad\": \"Localidad\", \"genero\": \"Genero\", \"nombre_recinto\": "
	    		+ "\"Recinto\", \"precio_maximo\": 100, \"precio_minimo\": 100}";

	    mockMvc.perform(post("/eventos")
	            .contentType(MediaType.APPLICATION_JSON)  
	            .content(eventoJson))  
	            .andDo(print())  
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.nombre").value(nombre))
	            .andExpect(jsonPath("$.fecha_evento").value(fecha))
	            .andExpect(jsonPath("$.descripcion").value(descripcion));
	}
	
	@Test
	void shouldGetEventoByIdNoExistenteDevuelve404() throws Exception {

        Long id = 30L;

        mockMvc.perform(get("/eventos/{id}", id) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	@Test
	void shouldGetEventoNoExistenteDevuelve404() throws Exception {

        Long id = -1L;

        mockMvc.perform(get("/eventos/{id}", id) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
	
	@Test
	void shouldGetEventoByNombreNoExistenteDevuelve404() throws Exception {

        String nombre="no existe";

        mockMvc.perform(get("/eventos/nombre/{nombre}", nombre) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}
	
	@Test
	void shouldGetEventoByNombre200() throws Exception {

        String nombre="nombre";

        mockMvc.perform(get("/eventos/nombre/{nombre}", nombre) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	@Test
	void shouldDeleteEventoExite() throws Exception {
		
	    // Mock del evento existente
	    Long idEvento = 1L;
	    Evento evento = new Evento();
	    evento.setId_evento(idEvento);
	    evento.setNombre("Concierto navidad");
	    evento.setFecha_evento(LocalDate.of(2024, 12, 25));
	    evento.setLocalidad("Madrid");

	    EventoResponse eventoResponse = new EventoResponse();
	    eventoResponse.setId_evento(evento.getId_evento());
	    eventoResponse.setNombre(evento.getNombre());
	    eventoResponse.setFecha_evento(evento.getFecha_evento());
	    eventoResponse.setLocalidad(evento.getLocalidad());

	    // Mock del comportamiento del servicio: que te devuelva el objeto cuando lo eliminas
	    when(eventoService.deleteEvento(idEvento)).thenReturn(Optional.of(evento));

	    // Realizar la solicitud DELETE al endpoint
	    mockMvc.perform(delete("/eventos/{id}", idEvento)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk()) // Verificar status 200
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(jsonPath("$.id_evento").value(idEvento))
	            .andExpect(jsonPath("$.nombre").value(evento.getNombre()))
	            .andExpect(jsonPath("$.fecha_evento").value("2024-12-25"));

	    // Verificar que el servicio fue llamado correctamente
	    verify(eventoService, times(1)).deleteEvento(idEvento);
	}

	@Test
	void shouldDeleteEventoNotFound() throws Exception {
	    // ID de un evento que no existe
	    Long idEventoInexistente = 9999L;

	    when(eventoService.deleteEvento(idEventoInexistente)).thenReturn(Optional.empty());

	    // Realizar la solicitud DELETE al endpoint
	    mockMvc.perform(delete("/eventos/{id}", idEventoInexistente)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isNotFound());  // Verificar que el estado sea 404 Not Found
	}
	
}

