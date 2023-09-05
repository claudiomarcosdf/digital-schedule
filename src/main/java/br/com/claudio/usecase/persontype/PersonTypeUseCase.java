package br.com.claudio.usecase.persontype;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.persontype.gateway.PersonTypeGateway;
import br.com.claudio.entities.persontype.model.PersonType;
import jakarta.validation.constraints.NotBlank;


@Service
public class PersonTypeUseCase {
	
	private final PersonTypeGateway personTypeGateway;
	
	public PersonTypeUseCase(PersonTypeGateway personTypeGateway) {
		this.personTypeGateway = personTypeGateway;
	}
	
	public PersonType createPersonType(CreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		var personType = new PersonType(input.name());
		
		return personTypeGateway.create(personType);
	}
	
	public PersonType updatePersonType(UpdateInput input) throws RequiredObjectIsNullException {
		if (input == null) throw new RequiredObjectIsNullException();
		
		this.findPersonTypeById(input.id());
		
		var personType = new PersonType(input.id(), input.name());
		return personTypeGateway.update(personType);
	}
	
	public void deletePersonType(Long id) throws ResourceNotFoundException {
		var personType = personTypeGateway
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de pessoa não encontrada!"));
		
		personTypeGateway.delete(personType);
	}
	
	public PersonType findPersonTypeById(Long id) throws ResourceNotFoundException {
		return personTypeGateway
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de pessoa não encontrada!"));		
	}
	
	public PersonType findPersonTypeByName(String name) throws ResourceNotFoundException {
		return personTypeGateway
				.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de pessoa não encontrada!"));		
	}	
	
	public List<PersonType> findAllPersonType() {
		return personTypeGateway.findAll();
	}
	
	public record CreateInput(String name) {};
	public record UpdateInput(Long id, @NotBlank String name) {};

}
