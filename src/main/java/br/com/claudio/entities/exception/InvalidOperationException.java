package br.com.claudio.entities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidOperationException(String ex) {
		super(ex);
	}
}
