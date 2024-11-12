package com.vedruna.serverProject.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vedruna.serverProject.persistance.model.Developer;
import com.vedruna.serverProject.persistance.model.Project;
import com.vedruna.serverProject.persistance.model.Technology;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProjectDTO {
	
	private int project_id;
	private String project_name;
	private String description;
	private Date start_date;
	private Date end_date;
	private String repository_url;
	private String demo_url;
	private String picture;
	private StatusDTO status;
	
	private List<DeveloperDTO> developersHasProjectsDTO = new ArrayList<>();
	private List<TechnologyDTO> technologiesHasProjectsDTO = new ArrayList<>();
	
	public ProjectDTO (Project project) {
		this.project_id = project.getProjectId();
		this.project_name = project.getProjectName();
		this.description = project.getDescription();
		this.start_date = project.getStart_date();
		this.end_date = project.getEnd_date();
		this.repository_url = project.getRepository_url();
		this.demo_url = project.getDemo_url();
		this.picture = project.getPicture();
		
		StatusDTO statusDTO = new StatusDTO(project.getStatus());
		this.status = statusDTO;
		
		for(Developer developerProjects : project.getDevelopersHasProjects()) {
			DeveloperDTO developer = new DeveloperDTO(developerProjects);
			developersHasProjectsDTO.add(developer);
			
		}
		
		for(Technology technologyProjects : project.getTechnologiesHasProjects()) {
			TechnologyDTO techology = new TechnologyDTO(technologyProjects);
			technologiesHasProjectsDTO.add(techology);
		}
	}
	

}
