package br.com.claudio.entities.person.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.person.model.Person;

public interface PersonGateway {
	
	Person create(Person person);
	
	Person update(Person person);
	
	void delete(Person person);
	
	Optional<Person> findById(Long id);
	
	List<Person> findAll();
}
