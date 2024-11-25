package com.vedruna.serverProject.services.project;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;

public interface ProjectServiceI {
	
	// Método para obtener un listado de proyectos paginado
	Page<ProjectDTO> getAllProjects(Pageable pageable);
	
	// Método para obtener un listado paginado de proyecto por su nombre.
	Page<ProjectDTO> getProjectByName(String name, Pageable pageable);
	
	//Método para agregar un project, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>addProject(Project project);
	
	//Método para obtener un proyecto por ID
	Project findProjectById(int id);
	
	//Método para editar un project, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>editProject(int id, Project project);
	
	//Método para eliminar un proyecto, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>deleteProject(Project project);
	
	//Método para pasar un proyecto de Development a Testing, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>toTestingProyect(int id);
	
	//Método para pasar un proyecto de Testing a Production, devolviendo un ResponseEntity
	ResponseEntity<ApiResponse<Project>>toProductionProyect(int id);
	
	//Método que devuelve una lista de proyectos por su tecnología
	ResponseEntity<List<ProjectDTO>> getAllProjectsWithTechonolgy(String tech);
}
