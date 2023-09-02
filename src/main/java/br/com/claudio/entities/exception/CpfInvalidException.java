package br.com.claudio.entities.exception;

public class CpfInvalidException extends RuntimeException {
	
	private static final long serialVersionUID = 7596780301477679578L; 
	
    public CpfInvalidException() {
    	super("Cpf inválido!");
    }	

}
