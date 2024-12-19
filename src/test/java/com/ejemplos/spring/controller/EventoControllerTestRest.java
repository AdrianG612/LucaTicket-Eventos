package com.ejemplos.spring.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ejemplos.spring.model.Evento;
import com.ejemplos.spring.repository.EventoRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest
@AutoConfigureMockMvc
public class EventoControllerTestRest {

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:3333";
    }
    
    @Autowired
    private EventoRepository eventoRepository;
    
    @Test
    public void testCrearEvento_Correcto() throws Exception {
        // Crear un JSON representando un Evento válido
        String nuevoEventoJson = """
        {
            "nombre": "Evento de prueba",
            "descripcion": "Una prueba exitosa",
            "fecha_evento": "2025-06-15",
            "hora_evento": "20:30",
            "precio_minimo": 20.50,
            "precio_maximo": 50.00,
            "localidad": "Madrid",
            "genero": "Rock",
            "nombre_recinto": "Wanda Metropolitano"
        }
        """;

        RestAssured.given()
        	.contentType(ContentType.JSON)
        	.body(nuevoEventoJson)
        .when()
        	.post("/eventos") 
        .then()
        	.statusCode(201)  // Validar código de estado HTTP 201 (Created)
        	.contentType(ContentType.JSON)
        	.body("nombre", equalTo("Evento de prueba"))
        	.body("fecha_evento", equalTo("2025-06-15"))       
        	.body("precio_minimo", equalTo(20.50f))
        	.body("precio_maximo", equalTo(50.00f))
        	.body("localidad", equalTo("Madrid"))
        	.body("genero", equalTo("Rock"))
        	.body("nombre_recinto", equalTo("Wanda Metropolitano"));
    }
    
    @Test
    public void testCrearEvento_PrecioInvalido() {
        String eventoInvalidoJson = """
        {
            "nombre": "Evento Invalido",
            "descripcion": "Evento con precios incorrectos",
            "fecha_evento": "2025-10-10",
            "hora_evento": "19:00",
            "precio_minimo": 120.00,
            "precio_maximo": 10.00,
            "localidad": "Barcelona",
            "genero": "Jazz",
            "nombre_recinto": "RazzMatazz"
        }
        """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(eventoInvalidoJson)
        .when()
                .post("/eventos")
        .then()
                .statusCode(400)
                .body("message", containsString("No se pudo guardar ya que no se puede introducir un precio_minimo mayor o igual al precio_maximo y viceversa")); // Mensaje de error esperado
    }
    
    @Test
    public void testObtenerEventoPorId_NoEncontrado() {
        Long idMal = 999L;
        RestAssured.given()
        .when()
        	.get("/eventos/{id}", idMal)
        .then()
        	.statusCode(404) 
            .body("message", containsString("El evento con el id: " + idMal + " enviado no se encuentra en la base de datos"));
    }
    
    @Test
    public void testObtenerEventoPorId_Correcto() {
        // Crear un evento en la base de datos antes de realizar la prueba
    	Evento evento = new Evento();
        evento.setNombre("Concierto");
        evento.setDescripcion("Un evento genial");
        evento.setFecha_evento(LocalDate.now());
        evento.setHora_evento(LocalTime.now());
        evento.setPrecio_minimo(10.0);
        evento.setPrecio_maximo(50.0);
        evento.setLocalidad("Madrid");
        evento.setGenero("Musical");
        evento.setNombre_recinto("WiZink Center");

        evento = eventoRepository.save(evento);
        Long idEvento = evento.getId_evento();
        
        RestAssured.given()
        .when()
                .get("/eventos/{id}", idEvento)  
        .then()
                .statusCode(200)  // Esperamos 200 OK
                .contentType(ContentType.JSON)
                .body("id_evento", equalTo(idEvento.intValue())) // Asegurar que el ID coincide
                .body("nombre", notNullValue()); // Verificar que 'nombre' no sea null
    }

    @Test
    public void testEliminarEvento_Correcto() {
    	Evento evento = new Evento();
        evento.setNombre("Concierto");
        evento.setDescripcion("Un evento genial");
        evento.setFecha_evento(LocalDate.now());
        evento.setHora_evento(LocalTime.now());
        evento.setPrecio_minimo(10.0);
        evento.setPrecio_maximo(50.0);
        evento.setLocalidad("Madrid");
        evento.setGenero("Musical");
        evento.setNombre_recinto("WiZink Center");

        evento = eventoRepository.save(evento);
        Long idEvento = evento.getId_evento();

        // Primero verificamos que el evento existe
        RestAssured.given()
        .when()
                .get("/eventos/{id}", idEvento)
        .then()
                .statusCode(200);  

        // Enviamos la solicitud DELETE
        RestAssured.given()
        .when()
                .delete("/eventos/{id}", idEvento)
         .then()
                .statusCode(200);
                
        // Verificar que el evento ya no existe
        RestAssured.given()
        .when()
                .get("/eventos/{id}", idEvento)
        .then()
                .statusCode(404);  
    }

    
}
