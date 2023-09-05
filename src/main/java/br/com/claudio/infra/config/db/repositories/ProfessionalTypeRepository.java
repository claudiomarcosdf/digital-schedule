package br.com.claudio.infra.config.db.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.config.db.schemas.ProfessionalTypeSchema;

public interface ProfessionalTypeRepository extends JpaRepository<ProfessionalTypeSchema, Long> {
	
	Optional<ProfessionalType> findByName(String name);

}
