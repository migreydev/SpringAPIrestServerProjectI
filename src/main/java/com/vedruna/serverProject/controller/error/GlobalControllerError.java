package com.vedruna.serverProject.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.exceptions.ExceptionPageNotFound;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.persistance.model.ApiError;

@RestControllerAdvice
public class GlobalControllerError {
	
	// Maneja la excepción cuando una página no es encontrada.
	@ExceptionHandler(ExceptionPageNotFound.class)
	public ResponseEntity<ApiError> pageNotFound(ExceptionPageNotFound e) {
		 ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	// Maneja la excepción de error en la página.
	@ExceptionHandler(ExceptionErrorPage.class)
	public ResponseEntity<ApiError> handleErrorPageException(ExceptionErrorPage e) {
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	// Maneja la excepción cuando no se encuentra un proyecto en la base de datos.
	@ExceptionHandler(ExceptionProjectNotFound.class)
	public ResponseEntity<ApiError> projectNotFound(ExceptionProjectNotFound e) {
		 ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}


}
