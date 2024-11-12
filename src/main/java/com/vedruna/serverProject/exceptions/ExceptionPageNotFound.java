package com.vedruna.serverProject.exceptions;

public class ExceptionPageNotFound extends RuntimeException {
	
	public ExceptionPageNotFound(String  exception) {
		super("The page not found: " + exception);
	}

}
