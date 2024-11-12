package com.vedruna.serverProject.services.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.serverProject.dto.ProjectDTO;
import com.vedruna.serverProject.exceptions.ExceptionErrorPage;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.repository.project.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectServiceI{
	
	@Autowired
	private ProjectRepository projectRepository;

	
	/**
     * Obtiene todos los proyectos de la base de datos con paginación.
     *
     * @param pageable, objeto que contiene tanto el tamaño como el num de páginas.
     * @return Una página con los proyectos convertidos en ProjectDTO.
     */
	@Override
	public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        // Obtener la página de proyectos desde el repositorio
        Page<Project> projectsPage = projectRepository.findAll(pageable);

        // Convertir la lista de proyectos a la lista de ProjectDTOs
        List<ProjectDTO> projectsDTOs = new ArrayList<>();
        for (Project project : projectsPage) {
            ProjectDTO projectDTO = new ProjectDTO(project);
            projectsDTOs.add(projectDTO);
        }

        // Devolver la página con ProjectDTOs
        return new PageImpl<>(projectsDTOs, pageable, projectsPage.getTotalElements());
    }	

}
