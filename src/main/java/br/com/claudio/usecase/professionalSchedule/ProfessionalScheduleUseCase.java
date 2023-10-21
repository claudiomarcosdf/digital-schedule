package br.com.claudio.usecase.professionalSchedule;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.professional.gateway.ProfessionalGateway;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.entities.professionalSchedule.gateway.ProfessionalScheduleGateway;
import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;

@Service
public class ProfessionalScheduleUseCase {
	
	private final ProfessionalScheduleGateway professionalScheduleGateway;
	private final ProfessionalGateway professionalGateway;
	
	public ProfessionalScheduleUseCase(ProfessionalScheduleGateway professionalScheduleGateway, ProfessionalGateway professionalGateway) {
		this.professionalScheduleGateway = professionalScheduleGateway;
		this.professionalGateway = professionalGateway;
	}
	
	public ProfessionalSchedule createProfessionalSchedule(ProfessionalScheduleCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		//System.err.println("create");
		
		Professional professional = professionalGateway.findById(input.getProfessionalId())
				.orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado!"));
		
		ProfessionalSchedule professionalScheduleMapped = modelMapper().map(input, ProfessionalSchedule.class);

		//Se já existir agenda do Profissional só atualiza
		if (professional.hasSchedule()) throw new InvalidOperationException("Já existe agenda para este profissional!");

		ProfessionalSchedule professionalSchedule = professionalScheduleGateway.create(professionalScheduleMapped);
		//Seta a agenda na tabela Profissional
		professional.setProfessionalSchedule(professionalSchedule);
		professionalGateway.update(professional);
		
		return professionalSchedule;
	}
	
	public ProfessionalSchedule updateProfessionalSchedule(ProfessionalScheduleUpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		//System.err.println("update");
		
		professionalGateway.findById(input.getProfessionalId())
				.orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado!"));
		
		ProfessionalSchedule professionalScheduleMapped = modelMapper().map(input, ProfessionalSchedule.class);
		ProfessionalSchedule professionalSchedule = professionalScheduleGateway.update(professionalScheduleMapped);
		
		return professionalSchedule;
	}	

}
