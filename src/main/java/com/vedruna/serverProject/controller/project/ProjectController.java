package com.vedruna.serverProject.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	/**
	 * Método para guardar un proyecto.
	 * 
	 * @param project Objeto project.
	 * @return Una respuesta estructurada.
	 */
	@PostMapping("/projects")
	public ResponseEntity<ApiResponse<Project>> addProject(@RequestBody Project project){
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
	public ResponseEntity<ApiResponse<Project>> deleteProyect(@PathVariable int id){
		//Busca el proyecto por su ID
		Project project = projectService.findProjectById(id);
		//Devuelve el proyecto encontrado al repositorio para proceder a su eliminación
		return projectService.deleteProject(project);
	}


}
