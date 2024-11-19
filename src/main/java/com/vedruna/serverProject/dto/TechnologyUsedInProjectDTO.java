package com.vedruna.serverProject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "DTO para asociar tecnologías con proyectos")
public class TechnologyUsedInProjectDTO {
	
	@Schema(description = "ID de la tecnología a asociar", example = "1")
	private int technologyId;
	
	@Schema(description = "ID del proyecto al que se asociará la tecnología", example = "1")
	 private int projectId;

}
