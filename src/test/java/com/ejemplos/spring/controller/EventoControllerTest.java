package com.ejemplos.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.response.EventoResponse;
import com.ejemplos.spring.service.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EventoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private EventoService eventoService;
	
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
}
