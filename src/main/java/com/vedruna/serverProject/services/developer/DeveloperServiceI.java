package com.vedruna.serverProject.services.developer;

import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;

public interface DeveloperServiceI {
	
	//Método para agregar un developer, devolviendo un ResponseEntity con respuesta estructurada
	ResponseEntity<ApiResponse<Developer>>addDeveloper(Developer developer);

}
