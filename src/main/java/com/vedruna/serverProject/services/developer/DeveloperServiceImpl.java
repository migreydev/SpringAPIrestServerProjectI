package com.vedruna.serverProject.services.developer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.dto.DeveloperWorkedDTO;
import com.vedruna.serverProject.exceptions.ExceptionDeveloperNotFound;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperEmail;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperGithub;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperLinkedin;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.repository.developer.DeveloperRepository;
import com.vedruna.serverProject.persistance.repository.project.ProjectRepository;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI{
	
	@Autowired
	private DeveloperRepository developerRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * Crea un nuevo developer.
	 *
	 * Este método valida que los campos email, linkedinUrl y githubUrl no sean nulos ni vacíos,
	 * y verifica que no haya duplicados de estos valores en la base de datos ya que se encuentran como valores unicos UQ. Si todo es válido, guarda el developer 
	 * en la base de datos y devuelve una respuestaestructurada. Si alguno de los valores está en uso, 
	 * o si algún campo obligatorio está vacío, lanza una excepción.
	 *
	 * @param developer El objeto Developer a agregar.
	 * @return ResponseEntity que contiene la respuesta estructurada.
	 * 
	 * @throws ExceptionInvalidDeveloperEmail Si el email está vacío o ya está en uso.
	 * @throws ExceptionInvalidDeveloperLinkedin Si la URL de LinkedIn está vacía o ya está en uso.
	 * @throws ExceptionInvalidDeveloperGithub Si la URL de GitHub está vacía o ya está en uso.
	 * @throws RuntimeException Si ocurre un error al guardar al developer.
	 */
	@Override
	public ResponseEntity<ApiResponse<Developer>> addDeveloper(Developer developer) {
		
		//Validando que los campos email, linkedin y github no este nulos o vacios.
		if (developer.getEmail() == null || developer.getEmail().isEmpty()) {
            throw new ExceptionInvalidDeveloperEmail("Email cannot be null or empty.");
        }
		
		if(developer.getLinkedinUrl() == null || developer.getLinkedinUrl().isEmpty()) {
			throw new ExceptionInvalidDeveloperLinkedin("Linkedin cannot be null or empty.");
		}
		
		if(developer.getGithubUrl() == null || developer.getGithubUrl().isEmpty()) {
			throw new ExceptionInvalidDeveloperGithub("GitHub cannot be null or empty.");
		}
		
		//Comprobando si existe el email
		Optional<Developer> existEmail = developerRepository.findDeveloperByEmail(developer.getEmail());
		if(existEmail.isPresent()) {
			throw new ExceptionInvalidDeveloperEmail("Email is already in use");
		}
		
		//Comprobando si existe linkedin
		Optional<Developer> existLinkedin = developerRepository.findDeveloperByLinkedinUrl(developer.getLinkedinUrl());	
		if(existLinkedin.isPresent()) {
			throw new ExceptionInvalidDeveloperLinkedin("Linkedin is already in use");
		}
		
		//Comprobando si existe githubUrl
		Optional<Developer> existGitHub = developerRepository.findDeveloperByGithubUrl(developer.getGithubUrl());	
		if(existGitHub.isPresent()) {
			throw new ExceptionInvalidDeveloperGithub("GitHub is already in use");
		}
		
		try {
			 // Guardamos al Developer en la base de datos
			developerRepository.save(developer);
			
			// Creación de la respuesta
			ApiResponse<Developer> response = new ApiResponse<>(HttpStatus.CREATED, "Developer saved successfully.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		 } catch (Exception e) {
		     // Manejo de errores
		     throw new RuntimeException("An unexpected error occurred while saving the developer.", e);
		 }
	}

	/**
	 * Elimina un developer de la base de datos según su ID.
	 * 
	 * Este método busca un developer en la base de datos utilizando el ID proporcionado.
	 * Si el desarrollador existe, se elimina y se devuelve una respuesta estructurada. 
	 * Si el desarrollador no se encuentra, se lanza una excepción `ExceptionDeveloperNotFound` con un mensaje de error.
	 *
	 * @param id El ID del developer que se desea eliminar.
	 * @return Un objeto `ResponseEntity` que contiene una respuesta estructurada.
	 * @throws ExceptionDeveloperNotFound Si no se encuentra un developer con el ID especificado.
	 */
	@Override
	public ResponseEntity<ApiResponse<Developer>> deleteDeveloper(int id) {
		Optional<Developer> optionalDeveloper = developerRepository.findById(id); //Se obtiene el developer por su id y se almacena como optional
		
		//Si existe
		if(optionalDeveloper.isPresent()) {
			Developer developer = optionalDeveloper.get(); //Se alamcena como un object Developer
			developerRepository.delete(developer);//Se elimina de la bbdd
			//Se crea y devuelve una respuesta estructurada
			ApiResponse<Developer> response = new ApiResponse<>(HttpStatus.OK, "Developer delete correctly");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			throw new ExceptionDeveloperNotFound("Developer not found");
		}
	}

	/**
	 * Asocia un desarrollador a un proyecto específico, indicando que el desarrollador 
	 * ha trabajado en dicho proyecto. 
	 *
	 * @param developerWorkedDTO un DTO que contiene los IDs del desarrollador y del proyecto.
	 * @return una respuesta HTTP ApiResponse que devuelve una respuesta estructurada.
	 * @throws ExceptionDeveloperNotFound si el desarrollador con el ID proporcionado no existe.
	 * @throws ExceptionProjectNotFound si el proyecto con el ID proporcionado no existe.
	 */
	@Override
	public ResponseEntity<ApiResponse<Developer>> developerHasWorkedOnaProject(int developerId, DeveloperWorkedDTO developerWorkedDTO) {
		
		//Busca el developer por el id y se alamcena como Optional
		Optional<Developer> optionalDeveloper = developerRepository.findById(developerId);
		
		//Si no existe salta una excepción
		if (!optionalDeveloper.isPresent()) {
	        throw new ExceptionDeveloperNotFound("Developer not found");
	    }
		
		//Busca el project por el id y se almacena como Optional
		Optional<Project> optionalProject = projectRepository.findById(developerWorkedDTO.getProjectId());
		
		//Si no esta presenta salta excepción
		if (!optionalProject.isPresent()) {
	        throw new ExceptionProjectNotFound("Project not found");
	    }
		
		//Se obtiene del optional el developer
		Developer developer = optionalDeveloper.get();
		//Se obtiene del optional el project;
	    Project project = optionalProject.get();
	    
	    // Asociar el proyecto al developer
	    developer.getProjectsHasDevelopers().add(project);
	    
	    //Guardar los cambios
	    developerRepository.save(developer);
		
	    // Crear y devolver una respuesta estructurada
	    ApiResponse<Developer> response = new ApiResponse<>(HttpStatus.OK, "Developer associated with project successfully");
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
