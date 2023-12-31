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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.claudio.entities.exception.CpfInvalidException;
import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import jakarta.validation.UnexpectedTypeException;

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
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				"Parâmetro da requisição inválido!",
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
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
	
	@ExceptionHandler(UnexpectedTypeException.class)
	public final ResponseEntity<ExceptionResponse> handleUnexpectedTypeException(
			Exception ex, WebRequest request) {
		
		String message = "Campos inválidos";
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				message,
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
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
		
		String erroDefault = ex.getMessage() != null ? ex.getMessage() : "";
		
		//String constraintNameString = ((ConstraintViolationException)ex.getCause()).getMessage();
		
		String message = "Regra de integridade violada. propriedade: '";
		
		
		if (erroDefault.contains("unique constraint")) {
			message += erroDefault.substring(erroDefault.indexOf("Key (")+5,
					erroDefault.indexOf(")")) + "'";
		}
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				message,
				request.getDescription(false));
		
		//Key violation
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ExceptionResponse> handleConstraintViolationException(
			ConstraintViolationException ex, WebRequest request) {
		
		ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();
		String field = "";
		
		for (Node node : violation.getPropertyPath()) {
		    field = node.getName();
		}
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				"Dados inválidos para a propriedade "+field,
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
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
	
	@ExceptionHandler(CpfInvalidException.class)
	public final ResponseEntity<ExceptionResponse> handleCpfInvalidException(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidOperationException(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}	
	

}
