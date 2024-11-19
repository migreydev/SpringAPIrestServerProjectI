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
		this.dev_id = developer.getDevId();
		this.dev_name = developer.getDevName();
		this.dev_surname = developer.getDevSurname();
		this.email = developer.getEmail();
		this.linkedin_url = developer.getLinkedinUrl();
		this.github_url = developer.getGithubUrl();
	
	}
	

}
