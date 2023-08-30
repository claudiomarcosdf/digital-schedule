package br.com.claudio.infra.config.exception;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String msgDefault = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		
		String message = (!msgDefault.isEmpty() || !msgDefault.isBlank()) ? msgDefault : ex.getMessage();
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				message,
				request.getDescription(false));	
		
		//Responde com um 422 ao invés de 400 para os Beans não validados
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<ExceptionResponse> handleNotDataIntegrityViolationException(
			Exception ex, WebRequest request) {
		
//		String erroDefault = ex.getMessage();
//		
//		String message = "";
//		
//		if (erroDefault.contains("messageTemplate")) {
//			System.err.println(erroDefault);
//			message = erroDefault.substring(erroDefault.indexOf("messageTemplate=")+1,
//					erroDefault.indexOf("}")-1);
//		}
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		//Key violation
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleRequiredObjectIsNullException(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}
	

}
