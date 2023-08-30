package br.com.claudio.entities.persontype.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.persontype.model.PersonType;

public interface PersonTypeGateway {
	
	PersonType create(PersonType personType);
	PersonType update(PersonType personType);
	void delete(PersonType personType);
	
	Optional<PersonType> findById(Long id);
	List<PersonType> findAll();
}
