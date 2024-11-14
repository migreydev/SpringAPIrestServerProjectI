package com.vedruna.serverProject.dto;


import com.vedruna.serverProject.persistance.model.Technology;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechnologyDTO {
	
	private int tech_id;	
	private String tech_name;
	
	public TechnologyDTO(Technology technology) {
		this.tech_id = technology.getTechId();
		this.tech_name = technology.getTechName();
	}
}
