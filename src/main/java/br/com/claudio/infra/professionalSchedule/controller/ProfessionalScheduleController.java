package br.com.claudio.infra.professionalSchedule.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;
import br.com.claudio.infra.professionalSchedule.dto.RequestProfessionalScheduleCreate;
import br.com.claudio.infra.professionalSchedule.dto.RequestProfessionalScheduleUpdate;
import br.com.claudio.usecase.professionalSchedule.ProfessionalScheduleCreateInput;
import br.com.claudio.usecase.professionalSchedule.ProfessionalScheduleUpdateInput;
import br.com.claudio.usecase.professionalSchedule.ProfessionalScheduleUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professionalschedules")
public class ProfessionalScheduleController {
	
	private final ProfessionalScheduleUseCase ProfessionalScheduleUseCase;
	
	public ProfessionalScheduleController(ProfessionalScheduleUseCase ProfessionalScheduleUseCase) {
		this.ProfessionalScheduleUseCase = ProfessionalScheduleUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProfessionalSchedule createProfessionalSchedule(@RequestBody @Valid RequestProfessionalScheduleCreate request) {
		ProfessionalScheduleCreateInput professionalScheduleCreateInput = modelMapper()
				.map(request, ProfessionalScheduleCreateInput.class);
		
		return ProfessionalScheduleUseCase.createProfessionalSchedule(professionalScheduleCreateInput);
	}
	
	@PutMapping
	public ProfessionalSchedule updateProfessionalSchedule(@RequestBody @Valid RequestProfessionalScheduleUpdate request) {
		ProfessionalScheduleUpdateInput professionalScheduleUpdateInput = modelMapper()
				.map(request, ProfessionalScheduleUpdateInput.class);
		
		return ProfessionalScheduleUseCase.updateProfessionalSchedule(professionalScheduleUpdateInput);
	}	

}
