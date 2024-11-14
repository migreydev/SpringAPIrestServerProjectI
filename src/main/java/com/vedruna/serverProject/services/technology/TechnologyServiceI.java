package com.vedruna.serverProject.services.technology;


import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Technology;

public interface TechnologyServiceI {
	
	//Método para agregar una technology, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Technology>> addTechnology(Technology technology);
	

}
