package br.com.claudio.infra.config.db.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.config.db.schemas.PersonTypeSchema;

public interface PersonTypeRepository extends JpaRepository<PersonTypeSchema, Long> {

	Optional<PersonType> findByName(String name);

}
