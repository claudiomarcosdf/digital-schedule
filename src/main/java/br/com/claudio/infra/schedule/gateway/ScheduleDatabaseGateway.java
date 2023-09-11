package br.com.claudio.infra.schedule.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.schedule.gateway.ScheduleGateway;
import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.config.db.repositories.ScheduleRepository;
import br.com.claudio.infra.config.db.schemas.ScheduleSchema;

@Component
public class ScheduleDatabaseGateway implements ScheduleGateway {
	
	private final ScheduleRepository scheduleRepository;
	
	public ScheduleDatabaseGateway(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	public Schedule create(Schedule schedule) {
		ScheduleSchema scheduleSchema = toScheduleSchema(schedule);
		return toSchedule(scheduleRepository.save(scheduleSchema));
	}

	@Override
	public Schedule update(Schedule schedule) {
		ScheduleSchema scheduleSchema = toScheduleSchema(schedule);
		return toSchedule(scheduleRepository.save(scheduleSchema));
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Schedule> findById(Long id) {
		return scheduleRepository.findById(id)
				.map(schema -> toSchedule(schema));
	}
	
	@Override
	public List<Schedule> listActiveSchedules(Long professionalTypeId, Long professionalId) {
		return toScheduleList(scheduleRepository.listActiveSchedules(professionalTypeId, professionalId));
	}	
	
	private List<Schedule> toScheduleList(List<ScheduleSchema> listActiveSchedules) {
		List<Schedule> scheduleList = new ArrayList<>();
		
		for(ScheduleSchema scheduleSchema: listActiveSchedules) {
			scheduleList.add(toSchedule(scheduleSchema));
		}
		
		return scheduleList;
	}

	private ScheduleSchema toScheduleSchema(Schedule schedule) {
		return modelMapper().map(schedule,ScheduleSchema.class);
	}

	private Schedule toSchedule(ScheduleSchema scheduleSchema) {
		return modelMapper().map(scheduleSchema, Schedule.class);
	}
	
	

}
