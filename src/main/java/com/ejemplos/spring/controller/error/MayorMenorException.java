package com.ejemplos.spring.controller.error;

public class MayorMenorException extends RuntimeException{

	public MayorMenorException(String message) {
		super(message);
	}
	public MayorMenorException() {
		super("No se pudo guardar ya que no se puede introducir un precio_minimo mayor o igual al precio_maximo y viceversa");
	}
	
}
