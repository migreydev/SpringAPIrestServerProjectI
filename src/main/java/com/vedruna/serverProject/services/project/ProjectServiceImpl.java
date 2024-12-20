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
import com.vedruna.serverProject.dto.TechnologyDTO;
import com.vedruna.serverProject.exceptions.ExceptionInvalidProjectData;
import com.vedruna.serverProject.exceptions.ExceptionInvalidStatusData;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.exceptions.ExceptionTechnologyNotFound;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.model.Status;
import com.vedruna.serverProject.persistance.model.Technology;
import com.vedruna.serverProject.persistance.repository.project.ProjectRepository;
import com.vedruna.serverProject.persistance.repository.technology.TechnologyRepository;

@Service
public class ProjectServiceImpl implements ProjectServiceI{
	
	@Autowired
	private ProjectRepository projectRepository;
	

	@Autowired
	private TechnologyRepository technologyRepository;

	
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
	 * Recupera una lista paginada de proyectos que coinciden con el nombre proporcionado.  
	 * Si no se encuentran proyectos, lanza una excepción personalizada.  
	 *
	 * @param name el nombre de los proyectos a buscar
	 * @param pageable información de la paginación
	 * @return un Page de objetos ProjectDTO que coinciden con el nombre de búsqueda
	 * @throws ExceptionProjectNotFound si no se encuentran proyectos con el nombre especificado
	 */
	@Override
	public Page<ProjectDTO> getProjectByName(String name, Pageable pageable) {
		// Llama al repositorio para buscar proyectos que contengan el nombre especificado
		 Page<Project> projectsPage = projectRepository.findByProjectNameContaining(name, pageable);
		 
		// Verifica si la página de proyectos está vacía
		if (projectsPage.isEmpty()) {
			throw new ExceptionProjectNotFound("Page not found with name: " + name);
		}
		// Convierte cada Project en un ProjectDTO
		List<ProjectDTO> projectsDTO = new ArrayList<>();
		for(Project projectsBydName : projectsPage) {
			ProjectDTO projectDTOname = new ProjectDTO(projectsBydName);
			projectsDTO.add(projectDTOname);
		}
		
		// Devolver la página con ProjectDTOs
        return new PageImpl<>(projectsDTO, pageable, projectsPage.getTotalElements());	
	}

	/**
	 * Añade un nuevo proyecto.
	 * 
	 * Este método guarda un proyecto en el repositorio. Antes de hacerlo, valida que el nombre del proyecto no 
	 * sea nulo o vacío. Si el nombre no es válido, se lanza una excepción ExceptionInvalidProjectData. 
	 * Si el proyecto se guarda correctamente, se devuelve una respuesta con un mensaje. En caso de error, se lanza una excepcion RuntimeException.
	 *
	 * @param project que se va a añadir.
	 * @return Una respuesta estructurada de la clase ApiReponse.
	 * @throws ExceptionInvalidProjectData Si el nombre del proyecto es nulo o vacío.
	 * @throws RuntimeException Si ocurre un error al guardar el proyecto.
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> addProject(Project project) {
		
		Optional <Project> existProject = projectRepository.findByProjectName(project.getProjectName());
		
		//Si el nombre del proyecto es nulo o vácio
		if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
	       //Salta una excepcion indicando que el nombre del proyecto esta vacio.
	        throw new ExceptionInvalidProjectData("Project name cannot be null or empty.");
	    }
		
		// Si ya existe un proyecto con el mismo nombre
	    if (existProject.isPresent()) {
	        throw new ExceptionInvalidProjectData("Project name must be unique.");
	    }
		
		 // Validación de las fechas
	    if (project.getStart_date() == null || project.getEnd_date() == null) {
	        throw new ExceptionInvalidProjectData("Start date and end date cannot be null.");
	    }

	    // Validar que start_date no sea posterior a end_date
	    if (project.getStart_date().after(project.getEnd_date())) {
	        throw new ExceptionInvalidProjectData("Start date cannot be after end date.");
	    }
	    
	    if(project.getStatus().getStatus_id() == 0) {
	    	 throw new ExceptionInvalidProjectData("Project status cannot be null.");
	    }
	    
		try {
			//Guarda el proyecto en el repositorio.
			projectRepository.save(project);
			//Se almacena una respuesta estructurada de la clase ApiReponse.
			ApiResponse<Project> response = new ApiResponse<>(HttpStatus.CREATED, "Project saved correctly");
			//Devuelve una ResponseEntity con el codigo 201 indicando que se ha creado correctamente.
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		 }catch (Exception e) {
			 //Manejo de error
			 throw new RuntimeException("An unexpected error occurred while saving the project.", e);
		 }
		
	}
	
	/**
	 * Busca un proyecto por su id.
	 * 
	 * @param id El ID del proyecto a buscar.
	 * @return El proyecto correspondiente al id proporcionado.
	 * @throws ExceptionProjectNotFound Si no se encuentra un proyecto con el id dado.
	 */
	@Override
	public Project findProjectById(int id) {
		
		//Se obtiene el proyecto
		Optional<Project> projectOptional =  projectRepository.findByProjectId(id);
		
		// Verificamos si el proyecto existe 
		if(projectOptional.isPresent()) {
			Project project = projectOptional.get();// Si el proyecto está presente, lo obtenemos
			return project;  // Devolvemos el proyecto encontrado
			
		}else {
			// Si no se encuentra el proyecto, lanzamos una excepción
			throw new ExceptionProjectNotFound("Project not found");
		}	
	}


	/**
	 * Actualiza un proyecto existente por su ID.
	 * 
	 * @param id El ID del proyecto a actualizar.
	 * @param project EL Project con los nuevos datos para actualizar.
	 * Verifica que el nombre del proyecto sea único antes de realizar la actualización.
	 * Si el nombre del proyecto ya existe para otro proyecto distinto al actual, se lanza una 
	 * excepción
	 * @return Una respuesta estructurada de la clase ApiReponse.
	 * @throws ExceptionProjectNotFound Si no se encuentra un proyecto con el ID proporcionado.
	 * @throws RuntimeException Si ocurre un error durante la actualización del proyecto.
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> editProject(int id, Project project) {
	    // Obtiene el proyecto como Optional obtenido a traves del ID;
	    Optional<Project> projectOptional = projectRepository.findById(id);
	    
	    // Si el proyecto no está presente
	    if (!projectOptional.isPresent()) {
	        // Si no se encuentra el proyecto, lanzamos una excepción
	        throw new ExceptionProjectNotFound("Project not found");
	    }

	    try {
	        // Se almacena en una variable de tipo Project
	        Project updateProject = projectOptional.get();

	        // Verificación del nombre del proyecto no es ni nulo ni está vacío.
	        if (project.getProjectName() != null && !project.getProjectName().isEmpty()) {

	            // Comprobamos si ya existe un proyecto con el mismo nombre, excluyendo el proyecto que estamos editando
	            Optional<Project> existingProject = projectRepository.findByProjectName(project.getProjectName());
	            
	            // Si el proyecto con el mismo nombre ya existe y no es el mismo proyecto que estamos editando, lanzamos la excepción
	            if (existingProject.isPresent() && existingProject.get().getProjectId() != updateProject.getProjectId()) {
	                throw new ExceptionInvalidProjectData("Project name must be unique and cannot be duplicated.");
	            }

	            // Actualizamos el nombre del proyecto si es diferente
	            updateProject.setProjectName(project.getProjectName());

	        } else {
	            // Si el nombre del proyecto está vacío o es nulo, lanzamos una excepción
	            throw new ExceptionInvalidProjectData("Project name cannot be null or empty.");
	        }

	        // Seteo de los valores restantes al objeto updateProject
	        updateProject.setDescription(project.getDescription());
	        updateProject.setStart_date(project.getStart_date());
	        updateProject.setEnd_date(project.getEnd_date());
	        updateProject.setRepository_url(project.getRepository_url());
	        updateProject.setDemo_url(project.getDemo_url());
	        updateProject.setPicture(project.getPicture());
	        updateProject.setStatus(project.getStatus());
	        updateProject.setDevelopersHasProjects(project.getDevelopersHasProjects());
	        updateProject.setTechnologiesHasProjects(project.getTechnologiesHasProjects());

	        // Se guarda el proyecto actualizado
	        projectRepository.save(updateProject);

	        // Se almacena una respuesta estructurada de la clase ApiResponse
	        ApiResponse<Project> response = new ApiResponse<>(HttpStatus.OK, "Project updated successfully");
	        return ResponseEntity.status(HttpStatus.OK).body(response);

	    } catch (Exception e) {
	        // Lanzamos una excepción más específica con el mensaje de error
	        throw new RuntimeException("An unexpected error occurred while updating the project.", e);
	    }
	}




	/**
	 * Elimina un proyecto.
	 * 
	 * Este método recibe un objeto `Project`, busca el proyecto correspondiente en 
	 * la base de datos utilizando su ID y, si se encuentra, lo elimina.
	 * 
	 * @param project El objeto `Project` que contiene el ID del proyecto a eliminar.
	 * @return Una respuesta estructurada de la clase ApiReponse.
	 * @throws ExceptionProjectNotFound Si no se encuentra un proyecto con el ID.
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> deleteProject(Project project) {
		//Se busca el proyecto por su id
		Optional<Project> optionalProject = projectRepository.findByProjectId(project.getProjectId());
		//Si esta presente en nuestra BBDD
		if(optionalProject.isPresent()) {
			//Se almacena el proyecto en un objeto de tipo Project para proceder a eliminarlo
			Project deleteProject = optionalProject.get();
			projectRepository.delete(deleteProject); //Se elimina el proyecto 
			//Se almacena una respuesta estructurada de la clase ApiReponse.
			ApiResponse<Project> response = new ApiResponse<>(HttpStatus.OK, "Project delete correctly");
			// Se devuelve una respuesta indicando que se ha eliminado
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}else {
			throw new ExceptionProjectNotFound("Project not found"); //Si el proyecto no es encontrado salta una excepción
		}
	}


	/**
	 * Cambia el estado de un proyecto de "Development" a "Testing", validando que 
	 * el estado inicial sea "Development" antes de realizar la actualización. 
	 * Establece el ID del estado correspondiente en el proyecto y lo actualiza en la base de datos.
	 *
	 * @param id el ID del proyecto que se desea actualizar.
	 * @return una respuesta HTTP estructurada ApiResponse que incluye 
	 *         un mensaje y estado.
	 * @throws ExceptionProjectNotFound si el proyecto con el ID especificado no existe.
	 * @throws ExceptionInvalidStatusData si el estado actual del proyecto no es "Development".
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> toTestingProyect(int id) {
		Optional<Project> optionalProject = projectRepository.findByProjectId(id);
		
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
					
			// Se valida que el estado inicial del proyecto sea "Development" (ID = 1)
			if(project.getStatus().getStatus_id() == 1) {	
				//Se crea un objeto Status
				Status statusTesting = new Status();
				//Se setea para obtener el id que almacena el valor de testing
				statusTesting.setStatus_id(2);
				//Se lo seteamos al project
				project.setStatus(statusTesting);
				//Se actualiza en la bbdd
				projectRepository.save(project);
				//Se almacena una respuesta estructurada de la clase ApiReponse.
				ApiResponse<Project> response = new ApiResponse<>(HttpStatus.OK, "Project update to Testing correctly");
				// Se devuelve una respuesta estructurada de la clase ApiResponse
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				throw new ExceptionInvalidStatusData("The initial status should be in development"); 
			}			
				
		}else {
			throw new ExceptionProjectNotFound("Project not found"); 
		}
	}


	/**
	 * Cambia el estado de un proyecto de "Testing" a "In Production", validando que 
	 * el estado inicial sea "Testing" antes de realizar la actualización. 
	 * Establece el ID del estado correspondiente en el proyecto y lo actualiza en la base de datos.
	 *
	 * @param id el ID del proyecto que se desea actualizar.
	 * @return una respuesta HTTP estructurada con ApiResponse que incluye 
	 *         un mensaje y estado con respuesta estructurada.
	 * @throws ExceptionProjectNotFound si el proyecto con el ID especificado no existe.
	 * @throws ExceptionInvalidStatusData si el estado actual del proyecto no es "Testing".
	 */
	@Override
	public ResponseEntity<ApiResponse<Project>> toProductionProyect(int id) {
		Optional<Project> optionalProject = projectRepository.findByProjectId(id);
		
		if(optionalProject.isPresent()) {
			Project project = optionalProject.get();
					
			// Se valida que el estado inicial del proyecto sea "Testing" (ID = 2)
			if(project.getStatus().getStatus_id() == 2) {	
				//Se crea un objeto Status
				Status statusTesting = new Status();
				//Se setea para obtener el id que almacena el valor de "In Production"
				statusTesting.setStatus_id(3);
				//Se lo seteamos al project
				project.setStatus(statusTesting);
				//Se actualiza en la bbdd
				projectRepository.save(project);
				//Se almacena una respuesta estructurada de la clase ApiReponse.
				ApiResponse<Project> response = new ApiResponse<>(HttpStatus.OK, "Project update to in Production correctly");
				// Se devuelve una respuesta estructurada de la clase ApiResponse
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				throw new ExceptionInvalidStatusData("The initial status should be in Testing"); 
			}			
				
		}else {
			throw new ExceptionProjectNotFound("Project not found"); 
		}
	}

	/**
	 * Obtiene todos los proyectos asociados a una tecnología específica.
	 * 
	 * @param tech el nombre de la tecnología a buscar.
	 * @return un ResponseEntity que contiene una lista de objetos ProjectDTO, 
	 *         representando los proyectos asociados a la tecnología especificada.
	 * @throws ExceptionTechnologyNotFound si la tecnología no se encuentra en la base de datos.
	 */
	@Override
	public ResponseEntity<List<ProjectDTO>> getAllProjectsWithTechonolgy(String tech) {
		//Buscamos la tecnología
	    Optional<Technology> optionalTechnology = technologyRepository.findTechnologyByTechName(tech);
	    //Si esta presente
	    if(optionalTechnology.isPresent()) {
	        Technology technology = optionalTechnology.get();//Se almacena como objetct Technology
	        
	        // Buscar los proyectos asociados a la tecnología
	        List<Project> projects = projectRepository.findProjectByTechnologiesHasProjects(technology);
	        
	        // Crear la lista de PRojectDTOs
	        List<ProjectDTO> projectDTOList = new ArrayList<>(); 
	        
	        for(Project project : projects) {
	            // Crear el DTO para cada project
	            ProjectDTO projectDTO = new ProjectDTO(project);
	            
	            // Agregar las tecnologías asociadas al proyecto y la convertimos a DTO
	            List<TechnologyDTO> technologiesDTO = new ArrayList<>();
	            //Iteramos sobre la lista de tecnologia que tienen proyectos
	            for(Technology technologyProjects : project.getTechnologiesHasProjects()) { 
	            	//Se crea el DTO para cada tecnología
	                TechnologyDTO techologyDTO = new TechnologyDTO(technologyProjects);
	                technologiesDTO.add(techologyDTO);
	            }
	            
	            // Asocia las tecnologías al proyecto
	            projectDTO.setTechnologiesHasProjectsDTO(technologiesDTO);
	            
	            // Añadir el DTO del proyecto a la lista
	            projectDTOList.add(projectDTO);
	        }

	        // Devuelve la lista de proyectos como ResponseEntity
	        return ResponseEntity.ok(projectDTOList);
	    } else {
	    	throw new ExceptionTechnologyNotFound("Technology not found"); 
	    }
	}



}
