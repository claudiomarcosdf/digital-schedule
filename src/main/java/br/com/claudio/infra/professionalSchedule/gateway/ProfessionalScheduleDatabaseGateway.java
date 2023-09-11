package br.com.claudio.infra.professionalSchedule.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.professionalSchedule.gateway.ProfessionalScheduleGateway;
import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;
import br.com.claudio.infra.config.db.repositories.ProfessionalScheduleRepository;
import br.com.claudio.infra.config.db.schemas.ProfessionalScheduleSchema;

@Component
public class ProfessionalScheduleDatabaseGateway implements ProfessionalScheduleGateway {
	
	private ProfessionalScheduleRepository professionalScheduleRepository;
	
	public ProfessionalScheduleDatabaseGateway(ProfessionalScheduleRepository professionalScheduleRepository) {
		this.professionalScheduleRepository = professionalScheduleRepository;
	}

	@Override
	public ProfessionalSchedule create(ProfessionalSchedule professionalSchedule) {
		ProfessionalScheduleSchema professionalScheduleSchema = toProfessionalScheduleSchema(professionalSchedule);
		return toProfessionalSchedule(professionalScheduleRepository.save(professionalScheduleSchema));
	}

	@Override
	public ProfessionalSchedule update(ProfessionalSchedule professionalSchedule) {
		ProfessionalScheduleSchema professionalScheduleSchema = toProfessionalScheduleSchema(professionalSchedule);
		return toProfessionalSchedule(professionalScheduleRepository.save(professionalScheduleSchema));
	}
	
	private ProfessionalScheduleSchema toProfessionalScheduleSchema(ProfessionalSchedule professionalSchedule) {
		return modelMapper().map(professionalSchedule, ProfessionalScheduleSchema.class);
	}
	
	private ProfessionalSchedule toProfessionalSchedule(ProfessionalScheduleSchema professionalScheduleSchema) {
		return modelMapper().map(professionalScheduleSchema, ProfessionalSchedule.class);
	}

	

}
