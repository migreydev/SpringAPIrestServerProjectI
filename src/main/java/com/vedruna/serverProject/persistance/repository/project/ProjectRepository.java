package com.vedruna.serverProject.persistance.repository.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.model.Technology;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	// Busca un Project por su nombre y devuelve un Optional para manejar el caso de no encontrarlo.
	Optional<Project> findByProjectName(String projectName);
	
	// Busca un Project por su id y devuelve un Optional para manejar el caso de no encontrarlo.
	Optional<Project> findByProjectId(int id);
	
	//Devuelve una lista de proyectos por su tecnología
	List<Project> findProjectByTechnologiesHasProjects(Technology tech);

}
