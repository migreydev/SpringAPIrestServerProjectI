package com.vedruna.serverProject.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class URLValidator implements ConstraintValidator<Url, String> {

	private static final String URL_REGEX = "^(https?:\\/\\/)[\\w-]+(\\.[\\w-]+)+(\\/\\S*)?$";

	/**
     * Inicializa el validador.
     * Este método se puede usar para configurar el validador según la Url.
     * 
     * @param constraintAnnotation instancia de la anotación Url.
     */
    @Override
    public void initialize(Url constraintAnnotation) {
    }
    
    /**
     * Valida que el valor proporcionado cumpla con el formato de una URL válida.
     * 
     * @param value el valor a validar.
     * @param context permite agregar detalles adicionales.
     * @return true si el valor es una URL válida; false si no lo es.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return Pattern.matches(URL_REGEX, value);
    }
}
