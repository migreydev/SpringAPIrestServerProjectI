package com.vedruna.serverProject.dto;


import com.vedruna.serverProject.persistance.model.Developer;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DeveloperDTO {
	
	private int dev_id;
	private String dev_name;
	private String dev_surname;
	private String email;
	private String linkedin_url;
	private String github_url;
	
	public DeveloperDTO(Developer developer) {
		this.dev_id = developer.getDev_id();
		this.dev_name = developer.getDev_name();
		this.dev_surname = developer.getDev_surname();
		this.email = developer.getEmail();
		this.linkedin_url = developer.getLinkedin_url();
		this.github_url = developer.getGithub_url();
	
	}
	

}
