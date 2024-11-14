package com.vedruna.serverProject.controller.technology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Technology;
import com.vedruna.serverProject.services.technology.TechnologyServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {
	
	@Autowired
	TechnologyServiceI technologyService;
	
	/**
	 * Endpoint para agregar una nueva tecnología.
	 * 
	 * Este método recibe una solicitud POST con un objeto `Technology` en el body 
	 * y delega la lógica al servicio para validarlo y guardarlo en la base de datos.
	 * 
	 * @param technology El objeto `Technology` que contiene los datos de la nueva tecnología a agregar.
	 * @return Una respuesta estructurada del objeto `ApiResponse` que indica que 
	 *         la tecnología fue guardada correctamente.
	 */
	@PostMapping("/technologies")
	public ResponseEntity<ApiResponse<Technology>> addTechnology(@RequestBody Technology technology){
		return technologyService.addTechnology(technology);
	}

}
