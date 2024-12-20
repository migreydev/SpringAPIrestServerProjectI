package com.vedruna.serverProject.services.developer;

import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.dto.DeveloperWorkedDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;

public interface DeveloperServiceI {
	
	//Método para agregar un developer, devolviendo un ResponseEntity con respuesta estructurada
	ResponseEntity<ApiResponse<Developer>>addDeveloper(Developer developer);
	
	//Método para eliminar un developer, devolviendo un ResponseEntity con una respuesta estructurada.
	ResponseEntity<ApiResponse<Developer>> deleteDeveloper(int id);
	
	//Método para añadir un developer a un projecto, devolviendo un ResponseEntity con una respuesta estructura.
	ResponseEntity<ApiResponse<Developer>> developerHasWorkedOnaProject(int developerId, DeveloperWorkedDTO developerWorkedDTO);

}
