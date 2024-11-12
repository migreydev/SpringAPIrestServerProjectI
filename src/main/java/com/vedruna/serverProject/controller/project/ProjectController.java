package com.vedruna.serverProject.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.exceptions.ExceptionPageNotFound;
import com.vedruna.serverProject.services.project.ProjectServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {
	
	@Autowired
	ProjectServiceI projectService;
	
	
	 /**
     * Obtiene una lista paginada de proyectos.
     * 
     * @param page El número de página a obtener, 0.
     * @param size El tamaño de la página, 5.
     * @return ResponseEntity con una página de objetos ProjectDTO.
     * @throws ExceptionErrorPage Si el número de página o tamaño de página no es válido.
     */
	@GetMapping("/projects")
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
		
		// Validación de la paginación
        if (page < 0) {
            throw new ExceptionErrorPage("Page index must not be less than zero.");
        }
        if (size <= 0) {
            throw new ExceptionErrorPage("Page size must be greater than zero.");
        }

        // Crear el Pageable con los parámetros pasados
        Pageable pageable = PageRequest.of(page, size);
        
        //Manejo de excepciones
        try {
        	// Obtener los proyectos desde el servicio
            Page<ProjectDTO> projects = projectService.getAllProjects(pageable);
            if (projects.isEmpty() && page > 0) { //Si está vacia y es mayor a 0, controla la excepción
                throw new ExceptionPageNotFound("Page not found: the page index is out of range.");
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            throw new ExceptionPageNotFound("Page not found: the page index is out of range.");
        }
    }
	
	/**
	 * Método para obtener un proyecto por su nombre. Utiliza el parámetro de ruta {word}
	 * para buscar un proyecto por su nombre.
	 * 
	 * @param word El nombre del proyecto que se desea buscar.
	 * @return Devuelve un ResponseEntity que contiene un ProjectDTO, si el proyecto se encuentra.
	 *         Si el proyecto no se encuentra, devuelve una excepción.
	 */
	@GetMapping("/projects/{word}")
	public ResponseEntity<ProjectDTO> getProjectByNameWord(@PathVariable String word) {
		return projectService.getProjectByName(word);
	}


}
