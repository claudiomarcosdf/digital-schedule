package br.com.claudio.infra.professionaltype.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.persontype.dto.RequestCreate;
import br.com.claudio.infra.persontype.dto.RequestUpdate;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase.CreateInput;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase.UpdateInput;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professionaltypes")
public class ProfessionalTypeController {

	private final ProfessionalTypeUseCase professionalTypeUseCase;
	
	public ProfessionalTypeController(ProfessionalTypeUseCase professionalTypeUseCase) {
		this.professionalTypeUseCase = professionalTypeUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProfessionalType createProfessionalType(@RequestBody @Valid RequestCreate request) {
		
		return professionalTypeUseCase.createProfessionalType(new CreateInput(request.name()));
	}
	
	@PutMapping
	public ProfessionalType updateProfessionalType(@RequestBody @Valid RequestUpdate request) {
		
		return professionalTypeUseCase.updateProfessionalType(new UpdateInput(request.id(), request.name(), request.active())); 
	}
	
	@GetMapping("/{id}")
	public ProfessionalType findProfessionalTypeById(@PathVariable("id") Long id) {
		
		return professionalTypeUseCase.findProfessionalTypeById(id);
	}
	
	@GetMapping
	public List<ProfessionalType> allProfessionalType() {
		
		return professionalTypeUseCase.findAllProfessionalType();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProfessionalType(@PathVariable Long id) {
		professionalTypeUseCase.deleteProfessionalType(id);
	}	
	
}
