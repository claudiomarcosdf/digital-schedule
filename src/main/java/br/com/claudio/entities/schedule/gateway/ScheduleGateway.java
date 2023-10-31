package br.com.claudio.entities.schedule.gateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.schedule.model.Schedule;

public interface ScheduleGateway {
	
	Schedule create(Schedule schedule);

	Schedule update(Schedule schedule);
	
	void delete(Long id);
	
	Optional<Schedule> findById(Long id);
	
	List<Schedule> listActiveSchedules(Long professionalTypeId, Long professionalId, LocalDateTime startDateTime, LocalDateTime endDateTime);
	
	Boolean existsEventsByDate(Long professionalTypeId, Long professionalId, LocalDateTime startDateTime);

	List<Schedule> getSchedulesByDate(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
