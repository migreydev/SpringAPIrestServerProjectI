package com.vedruna.serverProject.controller.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.dto.DeveloperWorkedDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Developer;
import com.vedruna.serverProject.services.developer.DeveloperServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Tag(name = "Developers", description = "Endpoints relacionados con los desarrolladores")
public class DeveloperController {
	
	@Autowired
	DeveloperServiceI developerService;
	
	/**
	 * Crea un nuevo developer.
	 * Este método recibe un objeto Developer en el body de la solicitud, valida los datos, y lo guarda 
	 * en la base de datos. Respondiendo con una respuesta estructurada 
	 * @param developer El objeto Developer a agregar.
	 * @return ResponseEntity con una respuesta estructurada
	 */
	@PostMapping("/developers")
	@Operation(summary = "Crear un nuevo developer", 
    description = "Este endpoint permite crear un nuevo developer y guardarlo en la base de datos.")
	public ResponseEntity<ApiResponse<Developer>> addDeveloper(@RequestBody Developer developer){
		return developerService.addDeveloper(developer);
	}
	
	/**
	 * Elimina un developer según su ID.
	 * 
	 * Este método maneja las solicitudes HTTP DELETE en la ruta "/developers/{id}",
	 * donde "{id}" es el ID del developer que se desea eliminar.
	 * Si la eliminación es correcta, devuelve una respuesta estructurada
	 * Si ocurre un error, se devuelve una respuesta estructura con su código de estado y mensaje correspondiente.
	 *
	 * @param id El ID único del developer a eliminar.
	 * @return Un objeto `ResponseEntity` que contiene una respuesta estructurada
	 */
	@DeleteMapping("/developers/{id}")
	@Operation(summary = "Eliminar un developer", 
    description = "Elimina un developer de la base de datos por su ID.")
	public ResponseEntity<ApiResponse<Developer>> deleteDeveloper(@PathVariable int id){
		return developerService.deleteDeveloper(id);
	}

	/**
	 * Endpoint para asociar un desarrollador a un proyecto, indicando que ha trabajado en él.
	 *
	 * @param developerWorkedDTO un objeto que contiene los IDs del desarrollador y del proyecto.
	 * @return una respuesta HTTP ApiResponse que incluye una respuesta estructurada.
	 */
	@PostMapping("/developers/worked/{id}")
	@Operation(summary = "Asociar un desarrollador a un proyecto", description = "Permite asociar un desarrollador existente con un proyecto.")
	public ResponseEntity<ApiResponse<Developer>> developerHasWorkedOnaProject(@PathVariable int id, @RequestBody DeveloperWorkedDTO developerWorkedDTO){
		return developerService.developerHasWorkedOnaProject(id, developerWorkedDTO);
	}
	
}
