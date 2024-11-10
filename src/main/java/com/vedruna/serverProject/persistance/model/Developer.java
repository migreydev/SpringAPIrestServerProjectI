package com.vedruna.serverProject.persistance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private int dev_id;
	
	@Column(name="dev_name")
	private String dev_name;
	
	@Column(name="dev_surname")
	private String dev_surname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="linkedin_url")
	private String linkedin_url;
	
	@Column(name="github_url")
	private String github_url;
	
	

}