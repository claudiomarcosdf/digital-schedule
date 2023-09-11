package br.com.claudio.usecase.person;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.CpfInvalidException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.person.gateway.PersonGateway;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.persontype.model.PersonType;
import static br.com.claudio.infra.config.validation.ValidCPF.isValidCPF;
import br.com.claudio.usecase.persontype.PersonTypeUseCase;

@Service
public class PersonUseCase {
	
	private final PersonGateway personGateway;
	
	private final PersonTypeUseCase personTypeUseCase;
	
	public PersonUseCase(PersonGateway personGateway, PersonTypeUseCase personTypeUseCase) {
		this.personGateway = personGateway;
		this.personTypeUseCase = personTypeUseCase;
	}
	
	public Person createPerson(PersonCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		/* Vefifica se cpf já existe no banco - o tratamento está sendo feito no método, pois
		 * é possível a inclusão sem o cpf, no primeiro momento
		 */
		if (input.getCpf() != null && !input.getCpf().equals("")) {
			if(!isValidCPF(input.getCpf())) throw new CpfInvalidException();
			if (personGateway.findByCpf(input.getCpf())) throw new DataIntegrityViolationException("Cpf já cadastrado!");
		}
		
		PersonType personType = personTypeUseCase.findPersonTypeById(input.getPersonType().getId());	
		
		Person person = modelMapper().map(input, Person.class);
		person.setPersonType(personType);
		
		return personGateway.create(person);
	}
	
	public Person updatePerson(PersonUpdateInput input) throws ResourceNotFoundException {
		if (input == null) throw new RequiredObjectIsNullException();
		/* Vefifica se cpf já existe no banco - o tratamento está sendo feito no método, pois
		 * é possível a inclusão sem o cpf, no primeiro momento
		 */
		if (input.getCpf() != null && !input.getCpf().equals("")) {
			if(!isValidCPF(input.getCpf())) throw new CpfInvalidException();
			if (personGateway.cpfAnotherPerson(input.getId(), input.getCpf())) throw new DataIntegrityViolationException("Cpf já cadastrado!");
		}		
				
		Person person = modelMapper().map(input, Person.class);
		return personGateway.update(person);
	}
	
	public Person findPersonById(Long id) throws ResourceNotFoundException {
		return personGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada!"));
	}
	
	public List<Person> list(String fullName, String cpf, Integer rg) {
		return personGateway.list(fullName, cpf, rg);
	}
	
	public List<Person> searchPersonByName(String fullName) {
		return personGateway.searchByName(fullName.toLowerCase());
	}	

	public void deletePerson(Long id) throws ResourceNotFoundException {
		var person = personGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada!"));
		
		person.setActive(false);
		personGateway.update(person);
	}

}
