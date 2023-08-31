package br.com.claudio.infra.config.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudio.infra.config.db.schemas.PersonSchema;

public interface PersonRepository extends JpaRepository<PersonSchema, Long> {

}
