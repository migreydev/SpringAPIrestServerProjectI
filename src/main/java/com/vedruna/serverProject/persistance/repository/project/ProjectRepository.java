package com.vedruna.serverProject.persistance.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.serverProject.persistance.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
