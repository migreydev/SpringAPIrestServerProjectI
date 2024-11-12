package com.vedruna.serverProject.persistance.repository.technology;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Integer>{

}
