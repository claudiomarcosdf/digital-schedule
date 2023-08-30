package br.com.claudio.entities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public RequiredObjectIsNullException() {
		super("Não é possível salvar um objeto nulo!");
	}	
	
	public RequiredObjectIsNullException(String ex) {
		super(ex);
	}

}
