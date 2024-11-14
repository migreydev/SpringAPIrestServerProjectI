package com.vedruna.serverProject.services.technology;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.exceptions.ExceptionInvalidTechnologyData;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Technology;
import com.vedruna.serverProject.persistance.repository.technology.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI{

	@Autowired
	private TechnologyRepository technologyRepository;

	/**
	 * Añade una nueva Technology.
	 * 
	 * Este método valida que los datos de la tecnología sean correctos antes de intentar guardarlos en la base de datos. 
	 * Si se encuentran problemas con los datos (como un ID de tecnología nulo o un nombre de tecnología vacío o duplicado), 
	 * se lanzará una excepción ExceptionInvalidTechnologyData.
	 * Si la tecnología se guarda correctamente, se retorna una respuesta estructurada.
	 * 
	 * @param technology La tecnología a agregar. Debe contener un ID y un nombre válidos.
	 * @return Una respuesta estructurado que indica que la tecnología fue guardada correctamente.
	 * @throws ExceptionInvalidTechnologyData Si el ID de la tecnología es 0, el nombre es nulo o vacío, 
	 * o si el nombre de la tecnología ya existe en la base de datos o si ocurre un error al intentar guardar la tecnología en la base de datos.
	 */
	@Override
	public ResponseEntity<ApiResponse<Technology>> addTechnology(Technology technology) {
		
		// Verificación de que el ID de la tecnología no sea 0.
		if (technology.getTechId() == 0) {
		    throw new ExceptionInvalidTechnologyData("Technology id cannot be 0.");
		}
		
		// Verificación de que el nombre de la tecnología no sea nulo ni vacío.
		if(technology.getTechName() == null ||  technology.getTechName().isEmpty()) {
			throw new ExceptionInvalidTechnologyData("Technology name cannot be null or empty.");
		}
		
		// Obtenemos la tegnologia existente a traves de su nombre
		Optional<Technology> technologyExist = technologyRepository.findTechnologyByTechName(technology.getTechName());
		//Si la tecnologia existe y el id es diferente al del bodyRequest entonces salta excepcion
		if(technologyExist.isPresent() && technologyExist.get().getTechId() != technology.getTechId()) {
			throw new ExceptionInvalidTechnologyData("Technology name must be unique and cannot be duplicated.");
		}
		
		try {
			//Si no tiene el mismo nombre se guarda
			technologyRepository.save(technology);
			//Se almacena una respuesta estructurada de la clase ApiReponse.
			ApiResponse<Technology> response = new ApiResponse<>(HttpStatus.CREATED, "Technology saved correctly");
			//Devuelve una ResponseEntity con el codigo 201 indicando que se ha creado correctamente.
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}catch (Exception e) {
			//Manejo de error
			throw new ExceptionInvalidTechnologyData("An unexpected error occurred while saving the technology.");
		}
		
	}
}
