package com.vedruna.serverProject.persistance.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApiResponse<T> {

	private String message;
    
    public ApiResponse(String message) {
        this.message = message;
    }
}
