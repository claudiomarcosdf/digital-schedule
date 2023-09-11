package br.com.claudio.entities.professionalSchedule.gateway;

import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;

public interface ProfessionalScheduleGateway {
	
	ProfessionalSchedule create(ProfessionalSchedule professionalSchedule);
	
	ProfessionalSchedule update(ProfessionalSchedule professionalSchedule);

}
