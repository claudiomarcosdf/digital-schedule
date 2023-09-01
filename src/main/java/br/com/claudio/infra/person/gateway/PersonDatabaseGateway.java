package br.com.claudio.infra.person.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.claudio.entities.person.gateway.PersonGateway;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.infra.config.db.repositories.PersonRepository;
import br.com.claudio.infra.config.db.schemas.PersonSchema;;

@Component
public class PersonDatabaseGateway implements PersonGateway {
	
	private final PersonRepository personRepository;
	
	public PersonDatabaseGateway(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Person create(Person person) {
		PersonSchema personSchema = toPersonSchema(person);
		personSchema.setActive(true);
  	    PersonSchema personSchemaSaved = personRepository.save(personSchema);
		
		System.err.println("GATEWAY - SAVE TO SCHEMA");
		System.err.println(personSchemaSaved.toString());
		
		return toPerson(personSchemaSaved);
	}

	@Override
	public Person update(Person person) {
		PersonSchema personSchema = toPersonSchema(person);
		PersonSchema personSchemaSaved = personRepository.save(personSchema);
		return toPerson(personSchemaSaved);
	}

	@Override
	public void delete(Person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Person> findById(Long id) {
		return personRepository.findById(id)
				.map(schema -> toPerson(schema));
	}

	@Override
	public List<Person> list(String fullName, String cpf, Integer rg) {
		PersonSchema personSchema = new PersonSchema();
		personSchema.setFullName(fullName);
		personSchema.setCpf(cpf);
		personSchema.setRg(rg);

		Example<PersonSchema> query = QueryBuilderPerson.makeQuery(personSchema);
				
		return toPersonList(personRepository.findAll(query));
	}
	
	@Override
	public List<Person> searchByName(String partialName) {
		return toPersonList(personRepository.searchByName(partialName));
	}	
	
	private PersonSchema toPersonSchema(Person person) {
		return modelMapper().map(person, PersonSchema.class);
	}

	private Person toPerson(PersonSchema personSchema) {
		return modelMapper().map(personSchema, Person.class);
	}
	
	private List<Person> toPersonList(List<PersonSchema> litPersonSchema) {
		
		List<Person> personList = new ArrayList<>();
		for (PersonSchema personSchema : litPersonSchema) {
			personList.add(toPerson(personSchema));
		}
		
		return personList;
	}

}
