package com.vedruna.serverProject.persistance.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="developers")
public class Developer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dev_id")
	private int devId;
	
	@Column(name="dev_name")
	private String devName;
	
	@Column(name="dev_surname")
	private String devSurname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="linkedin_url")
	private String linkedinUrl;
	
	@Column(name="github_url")
	private String githubUrl;
	
	@ManyToMany
	@JoinTable(name="developers_worked_on_projects", joinColumns= {@JoinColumn(name="developers_dev_id")}, inverseJoinColumns={@JoinColumn(name="projects_project_id")})
	private List<Project>projectsHasDevelopers = new ArrayList<>();
	
	

}
