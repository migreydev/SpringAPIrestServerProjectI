package com.vedruna.serverProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "DTO para asociar un trabajador con proyectos")
public class DeveloperWorkedDTO {
	
	@Schema(description = "ID del proyecto al que se asociará al trabajador", example = "1")
    private int projectId;

}
