package com.vedruna.serverProject.services.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.dto.ProjectDTO;

public interface ProjectServiceI {
	
	// Método para obtener un listado de proyectos paginado
	Page<ProjectDTO> getAllProjects(Pageable pageable);
	
	
}
