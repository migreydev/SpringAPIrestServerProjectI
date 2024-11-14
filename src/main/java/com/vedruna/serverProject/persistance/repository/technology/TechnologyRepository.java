package com.vedruna.serverProject.persistance.repository.technology;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Integer>{

	// Busca una techonology por su nombre y devuelve un Optional para manejar en caso de no encontrarlo.
	Optional<Technology> findTechnologyByTechName(String name);

}
