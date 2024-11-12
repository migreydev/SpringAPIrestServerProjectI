package com.vedruna.serverProject.services.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;

public interface ProjectServiceI {
	
	// Método para obtener un listado de proyectos paginado
	Page<ProjectDTO> getAllProjects(Pageable pageable);
	
	// Método para obtener un proyecto por su nombre, devolviendo un ResponseEntity
	ResponseEntity<ProjectDTO> getProjectByName(String name);
	
	//Método para agregar un project, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>addProject(Project project);
	
	
}
