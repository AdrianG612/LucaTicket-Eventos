package com.ejemplos.spring.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

//Trabaja de DTO
public class EventoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id_evento;
	
	// nombre no puede estar vacio
	@NotBlank(message = "El nombre del evento no puede estar vacío")
	private String nombre;
	private String descripcion;

	// La fecha tiene que tener determinado formato
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha_evento;
	
	// el horario debe tener el formato hora y minutos
	//@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "La hora debe estar en el formato HH:mm")
	private LocalTime hora_evento;

	// con la anotacion @PositiveOrZero comprobamos que solo pueda ser 0 o positivo;
	@PositiveOrZero(message="El precio minimo no puede ser negativo")
	private double precio_minimo;
	@PositiveOrZero(message="El precio maximo no puede ser negativo")
	private double precio_maximo;
	private String localidad;
	private String genero;
	
	// no puede estar vacio
	@NotBlank(message = "El nombre del recinto del evento no puede estar vacío")
	private String nombre_recinto;

	public Long getId_evento() {
		return id_evento;
	}

	public void setId_evento(Long id_evento) {
		this.id_evento = id_evento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha_evento() {
		return fecha_evento;
	}

	public void setFecha_evento(LocalDate fecha_evento) {
		this.fecha_evento = fecha_evento;
	}

	public LocalTime getHora_evento() {
		return hora_evento;
	}

	public void setHora_evento(LocalTime hora_evento) {
		this.hora_evento = hora_evento;
	}

	public double getPrecio_minimo() {
		return precio_minimo;
	}

	public void setPrecio_minimo(double precio_minimo) {
		this.precio_minimo = precio_minimo;
	}

	public double getPrecio_maximo() {
		return precio_maximo;
	}

	public void setPrecio_maximo(double precio_maximo) {
		this.precio_maximo = precio_maximo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNombre_recinto() {
		return nombre_recinto;
	}

	public void setNombre_recinto(String nombre_recinto) {
		this.nombre_recinto = nombre_recinto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
