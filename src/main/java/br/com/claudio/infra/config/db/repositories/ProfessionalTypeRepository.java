package br.com.claudio.infra.config.db.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.config.db.schemas.ProfessionalTypeSchema;

public interface ProfessionalTypeRepository extends JpaRepository<ProfessionalTypeSchema, Long> {
	
	Optional<ProfessionalType> findByName(String name);

	<S extends ProfessionalTypeSchema> List<S> findAll(Example<S> query);
}
