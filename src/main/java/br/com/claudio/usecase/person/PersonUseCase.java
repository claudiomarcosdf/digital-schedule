package br.com.claudio.usecase.person;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.person.gateway.PersonGateway;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.persontype.gateway.PersonTypeGateway;
import br.com.claudio.entities.persontype.model.PersonType;

@Service
public class PersonUseCase {
	
	private final PersonGateway personGateway;
	private final PersonTypeGateway personTypeGateway;
	
	public PersonUseCase(PersonGateway personGateway, PersonTypeGateway personTypeGateway) {
		this.personGateway = personGateway;
		this.personTypeGateway = personTypeGateway;
	}
	
	public Person createPerson(PersonCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		Person person = modelMapper().map(input, Person.class);
		
		PersonType personType = personTypeGateway.findById(input.getPersonTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de pessoa inválido"));
		
		person.setPersonType(personType);
		//System.err.println("USE CASE");
		//System.err.println(person.toString());
		
		return personGateway.create(person);
	}

}
