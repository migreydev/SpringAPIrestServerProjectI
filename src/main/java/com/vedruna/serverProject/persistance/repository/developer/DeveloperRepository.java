package com.vedruna.serverProject.persistance.repository.developer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Integer>{
	
	// Método que devuelve un Developer opcional por su email
	Optional<Developer> findDeveloperByEmail(String email);
	
	// Método que devuelve un Developer opcional por su linkedin
	Optional<Developer> findDeveloperByLinkedinUrl(String linkedinUrl);
	
	// Método que devuelve un Developer opcional por su gitHub
	Optional<Developer> findDeveloperByGithubUrl(String gitHub);

}
