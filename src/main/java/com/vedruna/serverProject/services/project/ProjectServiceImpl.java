package com.vedruna.serverProject.services.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.exceptions.ExceptionInvalidProjectData;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.repository.project.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectServiceI{
	
	@Autowired
	private ProjectRepository projectRepository;

	
	/**
     * Obtiene todos los proyectos de la base de datos con paginación.
     *
     * @param pageable, objeto que contiene tanto el tamaño como el num de páginas.
     * @return Una página con los proyectos convertidos en ProjectDTO.
     */
	@Override
	public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        // Obtener la página de proyectos desde el repositorio
        Page<Project> projectsPage = projectRepository.findAll(pageable);

        // Convertir la lista de proyectos a la lista de ProjectDTOs
        List<ProjectDTO> projectsDTOs = new ArrayList<>();
        for (Project project : projectsPage) {
            ProjectDTO projectDTO = new ProjectDTO(project);
            projectsDTOs.add(projectDTO);
        }

        // Devolver la página con ProjectDTOs
        return new PageImpl<>(projectsDTOs, pageable, projectsPage.getTotalElements());
    }


	/**
	 * Obtiene un proyecto por su nombre. Si el proyecto se encuentra en la base de datos, devuelve un 
	 * objeto `ProjectDTO`. Si no se encuentra el proyecto, lanza una excepción.
	 * 
	 * @param name El nombre del proyecto que se desea buscar.
	 * @return Un `ResponseEntity` que contiene el `ProjectDTO` si el proyecto existe.
	 * @throws ExceptionProjectNotFound Si el proyecto con el nombre especificado no se encuentra en la base de datos.
	 */
	@Override
	public ResponseEntity<ProjectDTO> getProjectByName(String name) {
		Optional<Project> projectOptional = projectRepository.findByProjectName(name); //Obtiene el project filtrado por su nombre
		
		if (projectOptional.isPresent()) {
			Project project = projectOptional.get(); //Obtiene el project como Project
			ProjectDTO projectDTO = new ProjectDTO(project); //Parsea el Project a ProjectDTO
			return ResponseEntity.ok(projectDTO); //Devuelve el dto en la respuesta
		 } else {
			// Si no se encuentra el proyecto, lanzamos la excepción
		        throw new ExceptionProjectNotFound("Project not found with name: " + name);  // Lanzamos la excepción
		    }
		
		
	}

	/**
	 * Añade un nuevo proyecto.
	 * 
	 * Este método guarda un proyecto en el repositorio. Antes de hacerlo, valida que el nombre del proyecto no 
	 * sea nulo o vacío. Si el nombre no es válido, se lanza una excepción ExceptionInvalidProjectData. 
	 * Si el proyecto se guarda correctamente, se devuelve una respuesta con un mensaje. En caso de error, se lanza una excepcion RuntimeException.
	 *
	 * @param project que se va a añadir.
	 * @return Un ResponseEntity con una clase ApiResponse generica que contiene un mensaje.
	 * @throws ExceptionInvalidProjectData Si el nombre del proyecto es nulo o vacío.
	 * @throws RuntimeException Si ocurre un error al guardar el proyecto.
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> addProject(Project project) {
		//Si el nombre del proyecto es nulo o vácio
		if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
	       //Salta una excepcion indicando que ambos campos estan vacios.
	        throw new ExceptionInvalidProjectData("Project name cannot be null or empty.");
	    }
		try {
			//Guarda el proyecto en el repositorio.
			projectRepository.save(project);
			//Se almacena un mensaje en la clase ApiReponse, indicando que se ha almacenado correctamente.
			ApiResponse<Project> response = new ApiResponse<>("Project saved correctly");
			//Devuelve una ResponseEntity con el codigo 201 indicando que se ha creado correctamente.
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		 }catch (Exception e) {
			 //Manejo de error
			 throw new RuntimeException("An unexpected error occurred while saving the project.", e);
		 }
		
	}
	

}
