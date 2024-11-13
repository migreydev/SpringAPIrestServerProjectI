package com.vedruna.serverProject.services.developer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperEmail;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperGithub;
import com.vedruna.serverProject.exceptions.ExceptionInvalidDeveloperLinkedin;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;
import com.vedruna.serverProject.persistance.repository.developer.DeveloperRepository;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI{
	
	@Autowired
	private DeveloperRepository developerRepository;

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

}
