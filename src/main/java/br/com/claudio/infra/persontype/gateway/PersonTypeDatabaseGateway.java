package br.com.claudio.infra.persontype.gateway;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.persontype.gateway.PersonTypeGateway;
import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.config.db.repositories.PersonTypeRepository;
import br.com.claudio.infra.config.db.schemas.PersonTypeSchema;

@Component
public class PersonTypeDatabaseGateway implements PersonTypeGateway{
	
	private final PersonTypeRepository personTypeRepository;
	
	public PersonTypeDatabaseGateway(PersonTypeRepository personTypeRepository) {
		this.personTypeRepository = personTypeRepository;
		
	}

	@Override
	public PersonType create(PersonType personType) {
		PersonTypeSchema personTypeSchema = personTypeRepository.save(new PersonTypeSchema(null, personType.getName()));
		return new PersonType(personTypeSchema.getId(), personTypeSchema.getName());
	}

	@Override
	public PersonType update(PersonType personType) {
		PersonTypeSchema personTypeSchema = personTypeRepository.save(new PersonTypeSchema(personType.getId(), personType.getName()));
		return new PersonType(personTypeSchema.getId(), personTypeSchema.getName());
	}

	@Override
	public void delete(PersonType personType) {
		personTypeRepository.deleteById(personType.getId());
	}

	@Override
	public Optional<PersonType> findById(Long id) {
		return personTypeRepository.findById(id)
				.map(schema -> new PersonType(schema.getId(), schema.getName()));
	}
	
	@Override
	public Optional<PersonType> findByName(String name) {
		return personTypeRepository.findByName(name)
				.map(schema -> new PersonType(schema.getId(), schema.getName()));
	}		
	
	@Override
	public List<PersonType> findAll() {
		return personTypeRepository.findAll()
				.stream()
				.map(schema -> new PersonType(schema.getId(), schema.getName()))
				.collect(toList());
	}


}
