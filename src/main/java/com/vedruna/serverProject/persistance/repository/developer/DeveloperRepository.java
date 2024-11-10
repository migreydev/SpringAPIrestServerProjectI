package com.vedruna.serverProject.persistance.repository.developer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Integer>{

}
