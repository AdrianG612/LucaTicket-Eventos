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
	private Long id_Evento;
	private String nombre;
	private String descripcion;
	private LocalDate fecha_evento;
	private LocalTime hora_evento;
	private double precio_minimo;
	private double precia_maximo;
	private String localidad;
	private String genero;
	private String nombre_recinto;
	
	public Evento() {
		super();
	}

	public Evento(Long id_Evento, String nombre, String descripcion, LocalDate fecha_evento, LocalTime hora_evento,
			double precio_minimo, double precia_maximo, String localidad, String genero, String nombre_recinto) {
		super();
		this.id_Evento = id_Evento;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_evento = fecha_evento;
		this.hora_evento = hora_evento;
		this.precio_minimo = precio_minimo;
		this.precia_maximo = precia_maximo;
		this.localidad = localidad;
		this.genero = genero;
		this.nombre_recinto = nombre_recinto;
	}

	public Long getId_Evento() {
		return id_Evento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDate getFecha_evento() {
		return fecha_evento;
	}

	public LocalTime getHora_evento() {
		return hora_evento;
	}

	public double getPrecio_minimo() {
		return precio_minimo;
	}

	public double getPrecia_maximo() {
		return precia_maximo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public String getGenero() {
		return genero;
	}

	public String getNombre_recinto() {
		return nombre_recinto;
	}

	public void setId_Evento(Long id_Evento) {
		this.id_Evento = id_Evento;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFecha_evento(LocalDate fecha_evento) {
		this.fecha_evento = fecha_evento;
	}

	public void setHora_evento(LocalTime hora_evento) {
		this.hora_evento = hora_evento;
	}

	public void setPrecio_minimo(double precio_minimo) {
		this.precio_minimo = precio_minimo;
	}

	public void setPrecia_maximo(double precia_maximo) {
		this.precia_maximo = precia_maximo;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setNombre_recinto(String nombre_recinto) {
		this.nombre_recinto = nombre_recinto;
	}

	@Override
	public String toString() {
	    return "Evento id: " + id_Evento + ": nombre " + nombre + "\n" +
	           "\tdescripcion: " + descripcion + "\n" +
	           "\tgenero: " + genero + "\n" +
	           "\tFecha y hora: " + fecha_evento + " " + hora_evento + "\n" +
	           "\tprecio desde " + precio_minimo + " a " + precia_maximo + "\n" +
	           "\tlocalidad: " + localidad + "\n" +
	           "\trecinto: " + nombre_recinto;
	}
	
}
