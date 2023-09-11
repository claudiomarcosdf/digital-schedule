package br.com.claudio.infra.config.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriodOfDay {
	
	String message() default "Período de horários inválido";
	
	boolean emptyValue() default false; //opção para valores nulos ou em branco
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
