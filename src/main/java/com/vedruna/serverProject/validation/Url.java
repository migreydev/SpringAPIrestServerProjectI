package com.vedruna.serverProject.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = URLValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {
    String message() default "La URL no es válida"; //Mensaje de error 
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
