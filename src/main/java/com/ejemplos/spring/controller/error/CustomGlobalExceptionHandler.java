package com.ejemplos.spring.controller.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ejemplos.spring.response.EventoResponse;



@ControllerAdvice
//Realmente seria mejor usar  @RestControllerAdvice aunque aqui es lo mismo
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// error handle for @Valid
	/*@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		logger.info("------ handleMethodArgumentNotValid()");
		
		CustomErrorJson customError = new CustomErrorJson();

		//Paso fecha pero la formatea a String con formato DD/MM/YY
		customError.setTimestamp(new Date());
		//customError.setTrace(ex.getLocalizedMessage());
		customError.setStatus(status.value());
		customError.setError(status.toString());
		
		// Get all errors indicando el campo en el que falla
		List<String> messages = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			messages.add(error.getField() + ": " + error.getDefaultMessage());
		}
		customError.setMessage(messages);
		
		//Para recoger el path y simular de forma completa los datos originales
		// request.getDescription(false) ---> uri=/students
		String uri = request.getDescription(false);
		uri = uri.substring(uri.lastIndexOf("=")+1);
		customError.setPath(uri);

		return new ResponseEntity<>(customError, headers, status);

	}*/
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    logger.info("------ handleMethodArgumentNotValid()");

	    CustomErrorJson customError = new CustomErrorJson();

	    // Paso fecha pero la formatea a String con formato DD/MM/YY
	    customError.setTimestamp(new Date());
	    customError.setStatus(status.value());
	    customError.setError(status.toString());

	    // Obtener los errores indicando el campo y el mensaje
	    List<String> messages = new ArrayList<>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        if ("fecha_evento".equals(error.getField())) { // Si el campo con error es "fecha_evento"
	            messages.add("El campo 'fecha_evento' tiene un formato incorrecto. Use el formato 'yy-MM-yyyy'.");
	        } else {
	            messages.add(error.getField() + ": " + error.getDefaultMessage());
	        }
	    }
	    customError.setMessage(messages);

	    // Para recoger el path y simular de forma completa los datos originales
	    String uri = request.getDescription(false);
	    uri = uri.substring(uri.lastIndexOf("=") + 1);
	    customError.setPath(uri);

	    return new ResponseEntity<>(customError, headers, status);
	}



}
