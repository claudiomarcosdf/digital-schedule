package br.com.claudio.infra.config.db.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudio.infra.config.db.schemas.PersonSchema;

public interface PersonRepository extends JpaRepository<PersonSchema, Long> {
	
	<S extends PersonSchema> List<S> findAll(Example<S> query);
	
	@Query("SELECT person FROM PersonSchema person WHERE " +
		  "person.active = true " +
		  "AND LOWER(person.fullName) LIKE %:partialName% "+
		  "ORDER BY person.fullName"
		  )
	List<PersonSchema> searchByName(String partialName);

}
