package br.com.claudio.infra.professional.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.infra.professional.dto.RequestProfessionalCreate;
import br.com.claudio.infra.professional.dto.RequestProfessionalUpdate;
import br.com.claudio.usecase.professional.ProfessionalCreateInput;
import br.com.claudio.usecase.professional.ProfessionalUpdateInput;
import br.com.claudio.usecase.professional.ProfessionalUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {
	
	private ProfessionalUseCase professionalUseCase;
	
	public ProfessionalController(ProfessionalUseCase professionalUseCase) {
		this.professionalUseCase = professionalUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Professional createProfessional(@RequestBody @Valid RequestProfessionalCreate request) {
		ProfessionalCreateInput professionalCreateInput = modelMapper().map(request, ProfessionalCreateInput.class);
		
		return professionalUseCase.createProfessional(professionalCreateInput);
	}
	
	@PutMapping
	public Professional updateProfessional(@RequestBody @Valid RequestProfessionalUpdate request) {
		ProfessionalUpdateInput professionalUpdateInput = modelMapper().map(request, ProfessionalUpdateInput.class);
		
		return professionalUseCase.updateProfessional(professionalUpdateInput);
	}
	
	@GetMapping("/{id}")
	public Professional findProfessionalById(@PathVariable("id") Long id) {
		return professionalUseCase.findProfessionalById(id);		
	}
	
	@GetMapping("/name/{partialName}")
	public List<Professional> searchProfessionalByName(@PathVariable("partialName") String partialName) {
		return professionalUseCase.searchProfessionalByName(partialName);
	}	
	
	@GetMapping("/professionaltype/{id}")
	public List<Professional> finByProfessionalTypeId(@PathVariable("id") Long id) {
		return professionalUseCase.findByProfessionalTypeId(id);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Professional> listOneOrAll(@RequestParam(required = false) String fullName, String cpf, Integer rg) {
		
		return professionalUseCase.list(fullName, cpf, rg);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProfessional(@PathVariable("id") Long id) {
		professionalUseCase.deleteProfessional(id);
	}	
	

}
