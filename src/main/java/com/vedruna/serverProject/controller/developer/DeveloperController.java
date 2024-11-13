package com.vedruna.serverProject.controller.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;
import com.vedruna.serverProject.services.developer.DeveloperServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {
	
	@Autowired
	DeveloperServiceI developerService;
	
	/**
	 * Crea un nuevo developer.
	 * Este método recibe un objeto Developer en el body de la solicitud, valida los datos, y lo guarda 
	 * en la base de datos. Si los datos son correctos, responde con una respuesta estructurada 
	 * @param developer El objeto Developer a agregar.
	 * @return ResponseEntity con una respuesta estructurada
	 * 
	 * @throws ExceptionInvalidDeveloperEmail Si el email ya está en uso.
	 * @throws ExceptionInvalidDeveloperLinkedin Si la URL de LinkedIn ya está en uso.
	 * @throws ExceptionInvalidDeveloperGithub Si la URL de GitHub ya está en uso.
	 */
	@PostMapping("/developers")
	public ResponseEntity<ApiResponse<Developer>> addDeveloper(@RequestBody Developer developer){
		return developerService.addDeveloper(developer);
	}

}
