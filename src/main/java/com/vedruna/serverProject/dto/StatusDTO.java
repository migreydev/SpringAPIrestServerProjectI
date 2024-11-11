package com.vedruna.serverProject.dto;

import com.vedruna.serverProject.persistance.model.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatusDTO {
	
	private int status_id;
	private String status_name;

	public StatusDTO(Status status) {
		this.status_id = status.getStatus_id();
		this.status_name = status.getStatus_name();
	}
}
