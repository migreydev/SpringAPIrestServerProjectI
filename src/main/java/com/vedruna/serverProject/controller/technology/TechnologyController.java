package com.vedruna.serverProject.controller.technology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.serverProject.dto.TechnologyUsedInProjectDTO;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Technology;
import com.vedruna.serverProject.services.technology.TechnologyServiceI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Tag(name = "Technologies", description = "Endpoints relacionados con las tecnologías")
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
	@Operation(summary = "Agregar nueva tecnología", 
    description = "Crea una nueva tecnología en la base de datos.")
	public ResponseEntity<ApiResponse<Technology>> addTechnology(@RequestBody Technology technology){
		return technologyService.addTechnology(technology);
	}
	
	/**
	 * Endpoint para eliminar una tecnología por su ID.
	 *
	 * Este método recibe un ID a través de la URL y llama al servicio correspondiente para eliminar la tecnología.
	 * Devuelve una respuesta estructurada.
	 *
	 * @param id El ID de la tecnología a eliminar.
	 * @return Una ResponseEntity con una respuesta estructurada con el estado 200 OK y un mensaje de éxito.
	 */
	@DeleteMapping("/technologies/{id}")
	@Operation(summary = "Eliminar tecnología", 
    description = "Elimina una tecnología específica utilizando su ID.")
	public ResponseEntity<ApiResponse<Technology>> deleteTechnology(@PathVariable int id){
		return technologyService.deleteTechnology(id);	
	}
	
	/**
	 * Endpoint para asociar una tecnología a un proyecto, indicando que la tecnología ha sido utilizada en el proyecto.
	 *
	 * @param technologyUsedInProjectDTO un objeto que contiene los IDs de la tecnología y del proyecto.
	 * @return una respuesta HTTP de @link ApiResponse estructurada.
	 */
	@PostMapping("/technologies/used/{id}")
	@Operation(summary = "Asociar tecnología a proyecto", 
    description = "Vincula una tecnología existente con un proyecto específico.")
	public ResponseEntity<ApiResponse<Technology>> technologyUsedInProject(@PathVariable int id, @RequestBody TechnologyUsedInProjectDTO technologyUsedInProjectDTO){
		return technologyService.technologyUsedInProject(id, technologyUsedInProjectDTO);
	}

}
