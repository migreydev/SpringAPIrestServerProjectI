package com.vedruna.serverProject.controller.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.exceptions.ExceptionPageNotFound;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.services.project.ProjectServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Tag(name = "Projects", description = "Endpoints relacionados con la gestión de proyectos")
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
	@Operation(summary = "Obtener todos los proyectos (paginados)", 
    description = "Devuelve una lista paginada de todos los proyectos en formato DTO.")
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
                throw new ExceptionPageNotFound("The page index is out of range.");
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            throw new ExceptionPageNotFound("The page index is out of range.");
        }
    }
	
	/**
	 * Obtiene un listado de proyectos cuyo nombre contenga una palabra específica, con paginación.
	 * 
	 * @param word La palabra a buscar en el nombre del proyecto.
	 * @param page El número de página a recuperar (por defecto 0).
	 * @param size El tamaño de la página (por defecto 5).
	 * @return Una respuesta HTTP que contiene una página de objetos ProjectDTO.
	 * @throws ExceptionErrorPage Si el índice de la página es menor que cero o el tamaño de la página es menor o igual a cero.
	 */
	@GetMapping("/projects/{word}")
	@Operation(summary = "Obtener proyecto por nombre", 
    description = "Busca un proyecto por su nombre exacto.")
	public ResponseEntity<Page<ProjectDTO>> getProjectByNameWord(@PathVariable String word,
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
        
     // Obtener los proyectos desde el servicio
     Page<ProjectDTO> projects = projectService.getProjectByName(word, pageable);
            
     return ResponseEntity.ok(projects);
        
	}
	
	/**
	 * Método para guardar un proyecto.
	 * 
	 * @param project Objeto project.
	 * @return Una respuesta estructurada.
	 */
	@PostMapping("/projects")
	@Operation(summary = "Crear un nuevo proyecto", 
    description = "Crea un nuevo proyecto en la base de datos.")
	public ResponseEntity<ApiResponse<Project>> addProject(@Valid @RequestBody Project project){
		return projectService.addProject(project);
	}
	
	/**
	 * Método que maneja la actualización de un proyecto existente.
	 * 
	 * Este método recibe un ID de proyecto a través de la URL y un objeto `Project` 
	 * en el cuerpo de la solicitud para actualizar los detalles del proyecto
	 * 
	 * @param id El ID del proyecto que se va a actualizar.
	 * @param updateProject El objeto Project que contiene los datos actualizados del proyecto.
	 * @return Una respuesta estructurada.
	 */
	@PutMapping("/projects/{id}")
	@Operation(summary = "Actualizar un proyecto", 
    description = "Actualiza un proyecto existente con los datos proporcionados.")
	public ResponseEntity<ApiResponse<Project>> updateProject(@PathVariable int id, @RequestBody Project updateProject) {	
		return projectService.editProject(id, updateProject);
	}
	
	
	/**
	 * Elimina un proyecto por su ID.
	 * 
	 * Este método maneja una solicitud HTTP DELETE para eliminar un proyecto de la base de datos. 
	 * Recibe un id del proyecto como parámetro y, si el proyecto existe, se procede 
	 * a su eliminación.
	 * 
	 * @param id El identificador único del proyecto que se desea eliminar.
	 * @return Una respuesta estructurada.
	 * En caso de eliminación, devuelve una respuesta indicando que el proyecto fué eliminado.
	 */
	@DeleteMapping("/projects/{id}")
	@Operation(summary = "Eliminar un proyecto", 
     description = "Elimina un proyecto de la base de datos utilizando su ID.")
	public ResponseEntity<ApiResponse<Project>> deleteProyect(@PathVariable int id){
		//Busca el proyecto por su ID
		Project project = projectService.findProjectById(id);
		//Devuelve el proyecto encontrado al repositorio para proceder a su eliminación
		return projectService.deleteProject(project);
	}
	
	/**
	 * Endpoint Patch para actualizar el estado de un proyecto a "Testing".
	 *
	 * @param id el ID del proyecto que se desea actualizar.
	 * @return una respuesta estructurada del objeto ApiResponse que incluye 
	 *         un mensaje y el estado.
	 */
	@PatchMapping("/projects/totesting/{id}")
	@Operation(summary = "Cambiar estado del proyecto a 'Testing'", 
    description = "Actualiza el estado del proyecto a 'Testing'.")
	public ResponseEntity<ApiResponse<Project>> toTestingProyect(@PathVariable int id){ 
		return projectService.toTestingProyect(id);
	}
	
	/**
	 * Endpoint Patch para actualizar el estado de un proyecto a "In Production".
	 *
	 * @param id el ID del proyecto que se desea actualizar.
	 * @return una respuesta estructurada del objeto ApiResponse que incluye 
	 *         un mensaje y el estado.
	 */
	@PatchMapping("/projects/toprod/{id}")
	@Operation(summary = "Cambiar estado del proyecto a 'In Production'", 
    description = "Actualiza el estado del proyecto a 'In Production'.")
	public ResponseEntity<ApiResponse<Project>> toProductionProyect(@PathVariable int id){ 
		return projectService.toProductionProyect(id);
	}
	
	/**
	 * Endpoint para obtener todos los proyectos asociados a una tecnología específica.
	 * 
	 * @param tech el nombre de la tecnología a buscar extraído de la URL.
	 * @return un ResponseEntity que contiene una lista de objetos ProjectDTO, 
	 *         representando los proyectos asociados a la tecnología especificada.
	 */
	@GetMapping("/projects/tec/{tech}")
	@Operation(summary = "Obtener proyectos por tecnología",
    description = "Devuelve una lista de proyectos asociados a una tecnología específica, indicada por su nombre.")
	public ResponseEntity<List<ProjectDTO>> getAllProjectsWithTechonolgy(@PathVariable String tech){
		return projectService.getAllProjectsWithTechonolgy(tech);
	}


}
