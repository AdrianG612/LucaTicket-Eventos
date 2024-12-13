package com.ejemplos.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
public class EventoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
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
}



