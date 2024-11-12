package com.vedruna.serverProject.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.exceptions.ExceptionPageNotFound;
import com.vedruna.serverProject.persistance.model.ApiError;

@RestControllerAdvice
public class GlobalControllerError {
	
	@ExceptionHandler(ExceptionPageNotFound.class)
	public ResponseEntity<ApiError> pageNotFound(ExceptionPageNotFound e) {
		 ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@ExceptionHandler(ExceptionErrorPage.class)
	public ResponseEntity<ApiError> handleErrorPageException(ExceptionErrorPage e) {
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}


}
