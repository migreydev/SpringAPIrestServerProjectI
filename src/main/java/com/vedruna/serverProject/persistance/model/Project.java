package com.vedruna.serverProject.persistance.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	private int projectId;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="start_date")
	private Date start_date;
	
	@Column(name="end_date")
	private Date end_date;
	
	@Column(name="repository_url")
	private String repository_url;
	
	@Column(name="demo_url")
	private String demo_url;
	
	@Column(name="picture")
	private String picture;
	
	@ManyToOne
    @JoinColumn(name = "status_status_id")
    private Status status;
	
	@ManyToMany(mappedBy="projectsHasDevelopers")
	private List<Developer>developersHasProjects = new ArrayList<>();
	
	@ManyToMany(mappedBy="projectsHasTechnologies")
	private List<Technology>technologiesHasProjects = new ArrayList<>();

}
