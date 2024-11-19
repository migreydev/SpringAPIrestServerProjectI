package com.vedruna.serverProject.services.technology;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.dto.TechnologyUsedInProjectDTO;
import com.vedruna.serverProject.exceptions.ExceptionInvalidTechnologyData;
import com.vedruna.serverProject.exceptions.ExceptionProjectNotFound;
import com.vedruna.serverProject.exceptions.ExceptionTechnologyNotFound;
import com.vedruna.serverProject.persistance.model.ApiResponse;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.model.Technology;
import com.vedruna.serverProject.persistance.repository.project.ProjectRepository;
import com.vedruna.serverProject.persistance.repository.technology.TechnologyRepository;

@Service
public class TechnologyServiceImpl implements TechnologyServiceI{

	@Autowired
	private TechnologyRepository technologyRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

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

	/**
	 * Elimina una Technology por su ID.
	 * 
	 * Este método recibe un ID de tecnología, verifica si existe en la base de datos y, si es así, la elimina.
	 * Si la tecnología no se encuentra, lanza una excepción indicando que no se ha encontrado.
	 *
	 * @param id El ID de la tecnología a eliminar.
	 * @return Una respuesta estructurada con el estado 200 (OK) y un mensaje que indica que la tecnología fue eliminada correctamente.
	 * @throws ExceptionTechnologyNotFound Si no se encuentra la tecnología con el ID proporcionado.
	 */
	@Override
	public ResponseEntity<ApiResponse<Technology>> deleteTechnology(int id) {
		//Se obtiene la tecnologia a través del parametro ID
		Optional<Technology> optionalTechnology= technologyRepository.findById(id);
		
		//Si existe
		if(optionalTechnology.isPresent()) {
			//Se obtiene como object Technology
			Technology technolgy = optionalTechnology.get();
			//Se procede a la eliminación
			technologyRepository.delete(technolgy);
			//Respuesta estructurada
			ApiResponse<Technology> response = new ApiResponse<>(HttpStatus.OK, "Technology deleted correctly");
			//Devuelve una ResponseEntity con el codigo 200 indicando que se ha eliminado correctamente.
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		}else {
			throw new ExceptionTechnologyNotFound("Technology not found");
		}
	}

	/**
	 * Asocia una tecnología a un proyecto, indicando que la tecnología ha sido utilizada en el proyecto.
	 *
	 * @param technologyUsedInProjectDTO un objeto que contiene los IDs de la tecnología y del proyecto.
	 * @return una respuesta ApiResponse que incluye un respuesta estructurada
	 * @throws ExceptionTechnologyNotFound si la tecnología con el ID especificado no existe.
	 * @throws ExceptionProjectNotFound si el proyecto con el ID especificado no existe.
	 */
	@Override
	public ResponseEntity<ApiResponse<Technology>> technologyUsedInProject(
			TechnologyUsedInProjectDTO technologyUsedInProjectDTO) {
		//Se obtiene el technology como un optional 
		Optional<Technology> optionalTechnology = technologyRepository.findById(technologyUsedInProjectDTO.getTechnologyId());
		
		 // Si no existe, lanza una excepción
		if(!optionalTechnology.isPresent()) {
			throw new ExceptionTechnologyNotFound("Technology not found");
		}
		
		//Se obtiene un project como optional
		Optional<Project> optionalProject = projectRepository.findById(technologyUsedInProjectDTO.getProjectId());
		
		 // Si no existe, lanza una excepción
		if(!optionalProject.isPresent()) {
			throw new ExceptionProjectNotFound("Project not found");
		}
		
		// Se obtiene la tecnología y el proyecto
		Technology technology = optionalTechnology.get();
		Project project = optionalProject.get();
		// Se asocia el proyecto a la tecnología
		technology.getProjectsHasTechnologies().add(project);
		// Se actualiza la tecnología
		technologyRepository.save(technology);
		
		//crea una respuesta estructurada y se devuelve
		ApiResponse<Technology>response = new ApiResponse<>(HttpStatus.OK, "Developer associated with project successfully");
	    return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
