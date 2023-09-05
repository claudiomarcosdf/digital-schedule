package br.com.claudio.entities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfInvalidException extends RuntimeException {
	
	private static final long serialVersionUID = 7596780301477679578L; 
	
    public CpfInvalidException() {
    	super("Cpf inválido!");
    }	
}
