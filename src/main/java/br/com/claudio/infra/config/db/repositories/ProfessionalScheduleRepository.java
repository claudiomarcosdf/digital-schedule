package br.com.claudio.infra.config.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudio.infra.config.db.schemas.ProfessionalScheduleSchema;

public interface ProfessionalScheduleRepository extends JpaRepository<ProfessionalScheduleSchema, Long> {

}
