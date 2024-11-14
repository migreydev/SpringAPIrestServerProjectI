package com.vedruna.serverProject.persistance.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="technologies")
public class Technology {
	
	@Id
	@Column(name="tech_id")
	private int techId;
	
	@Column(name="tech_name")
	private String techName;
	
	@ManyToMany
	@JoinTable(name="technologies_used_in_projects", joinColumns= {@JoinColumn(name="technologies_tech_id")}, inverseJoinColumns={@JoinColumn(name="projects_project_id")})
	private List<Project>projectsHasTechnologies = new ArrayList<>();

}
