package br.com.claudio.infra.schedule.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.schedule.dto.RequestScheduleCreate;
import br.com.claudio.infra.schedule.dto.RequestScheduleUpdate;
import br.com.claudio.infra.schedule.dto.ScheduleResponse;
import br.com.claudio.usecase.schedule.ScheduleCreateInput;
import br.com.claudio.usecase.schedule.ScheduleUpdateInput;
import br.com.claudio.usecase.schedule.ScheduleUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
	
	private final ScheduleUseCase scheduleUseCase;
	
	public ScheduleController(ScheduleUseCase scheduleUseCase) {
		this.scheduleUseCase = scheduleUseCase;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Schedule createSchedule(@RequestBody @Valid RequestScheduleCreate request) {
		ScheduleCreateInput scheduleCreateInput = modelMapper().map(request, ScheduleCreateInput.class);
		return scheduleUseCase.createSchedule(scheduleCreateInput);
	}
	
	@PutMapping
	public Schedule updateSchedule(@RequestBody @Valid RequestScheduleUpdate request) {
		ScheduleUpdateInput scheduleUpdateInput = modelMapper().map(request, ScheduleUpdateInput.class);
		return scheduleUseCase.updateSchedule(scheduleUpdateInput);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSchedule(@PathVariable("id") Long id) {
		scheduleUseCase.deleteSchedule(id);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK) 
	public List<ScheduleResponse> listSchedule(@RequestParam Long professionalTypeId, Long professionalId, String startDate, String endDate) {
	
		return scheduleUseCase.listActiveSchedules(professionalTypeId, professionalId, startDate, endDate);
	}
	
	@PostMapping("/sendconfirmation")
	public ResponseEntity<String> sendConfirmationMessage(@RequestParam String scheduleDate) {
		
		Boolean error = scheduleUseCase.sendConfirmationMessage(scheduleDate);
		
		return error 
				? new ResponseEntity<>("Erro ao enviar mensagens de confirmação", HttpStatus.BAD_REQUEST) 
				: ResponseEntity.ok().body("OK");
	}	
	
}
