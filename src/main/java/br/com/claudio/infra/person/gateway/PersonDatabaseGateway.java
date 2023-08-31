package br.com.claudio.infra.person.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.person.gateway.PersonGateway;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.infra.config.db.repositories.PersonRepository;
import br.com.claudio.infra.config.db.schemas.PersonSchema;
import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;;

@Component
public class PersonDatabaseGateway implements PersonGateway {
	
	private final PersonRepository personRepository;
	
	public PersonDatabaseGateway(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Person create(Person person) {
		// falta persontype p/ persontypeschema
		
		PersonSchema personSchema = toPersonSchema(person);
		personSchema.setActive(true);
  	    PersonSchema personSchemaSaved = personRepository.save(personSchema);
		
		System.err.println("GATEWAY - SAVE TO SCHEMA");
		System.err.println(personSchemaSaved.toString());
		
		return toPerson(personSchemaSaved);
	}

	@Override
	public Person update(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Person> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private PersonSchema toPersonSchema(Person person) {
		return modelMapper().map(person, PersonSchema.class);
	}

	private Person toPerson(PersonSchema personSchema) {
		return modelMapper().map(personSchema, Person.class);
	}	

}
