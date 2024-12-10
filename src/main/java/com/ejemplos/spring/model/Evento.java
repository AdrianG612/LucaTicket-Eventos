package com.ejemplos.spring.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_evento;
	private String nombre;
	private String descripcion;
	private LocalDate fecha_evento;
	private LocalTime hora_evento;
	private double precio_minimo;
	private double precio_maximo;
	private String localidad;
	private String genero;
	private String nombre_recinto;

	public Evento() {
		super();
	}

	public Evento(Long id_evento, String nombre, String descripcion, LocalDate fecha_evento, LocalTime hora_evento,
			double precio_minimo, double precio_maximo, String localidad, String genero, String nombre_recinto) {
		super();
		this.id_evento = id_evento;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_evento = fecha_evento;
		this.hora_evento = hora_evento;
		this.precio_minimo = precio_minimo;
		this.precio_maximo = precio_maximo;
		this.localidad = localidad;
		this.genero = genero;
		this.nombre_recinto = nombre_recinto;
	}

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

}
