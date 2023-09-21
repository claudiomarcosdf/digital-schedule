package br.com.claudio.infra.config.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudio.infra.config.db.schemas.ScheduleSchema;

public interface ScheduleRepository extends JpaRepository<ScheduleSchema, Long> {
	
	@Query("SELECT schedule FROM ScheduleSchema schedule WHERE " +
			  "schedule.active = true " +
			  "AND schedule.professionalType.id = :professionalTypeId "+
			  "AND schedule.professional.id = :professionalId "+
			  "ORDER BY schedule.startDate"
	)	
	List<ScheduleSchema> listActiveSchedules(Long professionalTypeId, Long professionalId);
}