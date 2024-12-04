package com.vedruna.serverProject.services.technology;


import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.dto.TechnologyUsedInProjectDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Technology;

public interface TechnologyServiceI {
	
	//Método para agregar una technology, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Technology>> addTechnology(Technology technology);
	
	//Método para eliminar una technology, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Technology>> deleteTechnology(int id);
	
	//Metodo para añadir un technology a un project devolviendo un ResponseEntity.
	ResponseEntity<ApiResponse<Technology>> technologyUsedInProject(int technologyId, TechnologyUsedInProjectDTO technologyUsedInProjectDTO);
	

}
