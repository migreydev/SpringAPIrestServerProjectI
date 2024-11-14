package com.vedruna.serverProject.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.serverProject.exceptions.ExceptionDeveloperNotFound;
import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperEmail;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperGithub;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperLinkedin;
import com.vedruna.serverProject.exceptions.ExceptionInvalidProjectData;
import com.vedruna.serverProject.exceptions.ExceptionInvalidTechnologyData;
import com.vedruna.serverProject.exceptions.ExceptionPageNotFound;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.persistance.model.ApiError;

@RestControllerAdvice
public class GlobalControllerError {
	
	// Maneja la excepción cuando una página no es encontrada
	@ExceptionHandler(ExceptionPageNotFound.class)
	public ResponseEntity<ApiError> pageNotFound(ExceptionPageNotFound e) {
		 ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	// Maneja la excepción de error en la página
	@ExceptionHandler(ExceptionErrorPage.class)
	public ResponseEntity<ApiError> handleErrorPageException(ExceptionErrorPage e) {
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	//Maneja la excepción cuando los datos del proyecto no son válidos
	 @ExceptionHandler(ExceptionInvalidProjectData.class)
	 public ResponseEntity<ApiError> invalidProjectData(ExceptionInvalidProjectData e) {
	     ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
	     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	 
	 // Maneja la excepción cuando no se encuentra un proyecto
	 @ExceptionHandler(ExceptionProjectNotFound.class)
	 public ResponseEntity<ApiError> handleProjectNotFoundException(ExceptionProjectNotFound e) {
	      ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }
	 
	 // Maneja la excepción cuando el email del developer ya está en uso
	 @ExceptionHandler(ExceptionInvalidDeveloperEmail.class)
	 public ResponseEntity<ApiError> handleInvalidDeveloperEmail(ExceptionInvalidDeveloperEmail e) {
	      ApiError apiError = new ApiError(HttpStatus.CONFLICT, e.getMessage());
	      return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	 }
	 
	// Maneja la excepción cuando la url del linkedin del developer ya está en uso
	@ExceptionHandler(ExceptionInvalidDeveloperLinkedin.class)
	public ResponseEntity<ApiError> handleInvalidDeveloperLinkedin(ExceptionInvalidDeveloperLinkedin e) {
	     ApiError apiError = new ApiError(HttpStatus.CONFLICT, e.getMessage());
	     return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
	
	// Maneja la excepción cuando la url del gitHub del developer ya está en uso
	@ExceptionHandler(ExceptionInvalidDeveloperGithub.class)
	public ResponseEntity<ApiError> handleInvalidDeveloperGitHub(ExceptionInvalidDeveloperGithub e) {
		   ApiError apiError = new ApiError(HttpStatus.CONFLICT, e.getMessage());
		   return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
	
	// Maneja la excepción cuando no se encuentra un developer
	@ExceptionHandler(ExceptionDeveloperNotFound.class)
	public ResponseEntity<ApiError> handleDeveloperNotFoundException(ExceptionDeveloperNotFound e) {
		    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	 
	
	//Maneja la excepción cuando los datos del proyecto no son válidos
	@ExceptionHandler(ExceptionInvalidTechnologyData.class)
	public ResponseEntity<ApiError> invalidTechnologyData(ExceptionInvalidTechnologyData e) {
		     ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}


}
