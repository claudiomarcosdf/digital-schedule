package br.com.claudio.infra.config.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DayValidator implements ConstraintValidator<ValidPeriodOfDay, String> {
	
	private boolean emptyValue;
	
	@Override
	public void initialize(ValidPeriodOfDay constraintAnnotation) {
		this.emptyValue = constraintAnnotation.emptyValue();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// Testa se formado está no padrão correto: 08:00-12:00,13:00-18:00 ou 08:00-12:00,null ou null,14:00-22:00
		
		// Checa se aceita nulo/vazio ou nao
		if (emptyValue && (value == null || value.equals(""))) return true;
		if (!emptyValue && (value == null || value.equals(""))) return false;
		
		if (value.length() == 23) {
			//if (value.contains(":") && value.contains("-")) {

			if (validValueFormat(value)) {
				try {
					String horaInicialManha = value.substring(0, 5);
					String horaFinalManha = value.substring(6, 11);
					String horaInicialTarde = value.substring(12, 17); 
					String horaFinalTarde = value.substring(18, 23);
					
					//System.err.println(horaInicialManha);
					//System.err.println(horaFinalManha);
					//System.err.println(horaInicialTarde);
					//System.err.println(horaFinalTarde);
					
					return true;
				} catch (Exception e) {
					System.err.println(e);
					return false;
				}
			}
		} else if (value.length() == 16) {
			String newValue = value.replaceAll(",", "").replaceAll("null", "");
			//08:00-12:00
			if (validShortValueFormat(newValue)) {
				//08:00-12:00,null  or  null,14:00-18:00
				
				try {
					if (value.startsWith("null")) {
						String horaInicialTarde = value.substring(5, 10); 
						String horaFinalTarde = value.substring(11, 16);
					} else {
						String horaInicialManha = value.substring(0, 5);
						String horaFinalManha = value.substring(6, 11);
					}
					
					//System.err.println(horaInicialManha);
					//System.err.println(horaFinalManha);
					//System.err.println(horaInicialTarde);
					//System.err.println(horaFinalTarde);
					
					return true;
				} catch (Exception e) {
					System.err.println(e);
					return false;
				}				
				
			}
		}
				
		return false;
	}
	
	private Boolean validValueFormat(String value) {
		
        final Pattern pattern = Pattern.compile("^\\d\\d:\\d\\d-\\d\\d:\\d\\d,\\d\\d:\\d\\d-\\d\\d:\\d\\d$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        
        return matcher.matches();
	}
	
	private Boolean validShortValueFormat(String value) {
		
        final Pattern pattern = Pattern.compile("^\\d\\d:\\d\\d-\\d\\d:\\d\\d$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        
        return matcher.matches();
	}	

}
