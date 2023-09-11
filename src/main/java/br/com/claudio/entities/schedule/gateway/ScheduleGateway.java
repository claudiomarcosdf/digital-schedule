package br.com.claudio.entities.schedule.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.schedule.model.Schedule;

public interface ScheduleGateway {
	
	Schedule create(Schedule schedule);

	Schedule update(Schedule schedule);
	
	void delete(Long id);
	
	Optional<Schedule> findById(Long id);
	
	List<Schedule> listActiveSchedules(Long professionalTypeId, Long professionalId);

}
