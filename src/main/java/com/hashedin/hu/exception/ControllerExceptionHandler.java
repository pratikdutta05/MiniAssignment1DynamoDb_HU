package com.hashedin.hu.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

	@Value(value = "${data.exception.message1}")
	private String message1;
	
	@Value(value = "${data.exception.message2}")
	private String message2;

	@Value(value = "${data.exception.headerMissing}")
	private String headerMissingMessage;

	@Value(value = "${data.exception.defaultMessage}")
	private String defaultExceptionMessage;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				message1,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> resourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				message2,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(UnauthorisedException.class)
	public ResponseEntity<ErrorMessage> unauthorisedException(UnauthorisedException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.FORBIDDEN.value(),
				new Date(),
				headerMissingMessage,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(),
				defaultExceptionMessage,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}