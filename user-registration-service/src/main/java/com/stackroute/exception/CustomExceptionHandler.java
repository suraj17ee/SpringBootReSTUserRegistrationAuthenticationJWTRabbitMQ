package com.stackroute.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException pe) {
		return new ResponseEntity<>(pe.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserFileNotFoundException.class)
	public ResponseEntity<String> handleUserFileNotFoundException(UserFileNotFoundException ie) {
		return new ResponseEntity<>(ie.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
	}

}
