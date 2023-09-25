package br.com.claudio.infra.procedure.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.infra.procedure.dto.RequestProcedureCreate;
import br.com.claudio.infra.procedure.dto.RequestProcedureUpdate;
import br.com.claudio.usecase.procedure.ProcedureCreateInput;
import br.com.claudio.usecase.procedure.ProcedureUpdateInput;
import br.com.claudio.usecase.procedure.ProcedureUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/procedures")
public class ProcedureController {
	
	private final ProcedureUseCase procedureUseCase;
	
	public ProcedureController(ProcedureUseCase procedureUseCase) {
		this.procedureUseCase = procedureUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Procedure createProcedure(@RequestBody @Valid RequestProcedureCreate request) {
		ProcedureCreateInput procedureCreateInput = modelMapper().map(request, ProcedureCreateInput.class);
		return procedureUseCase.createProcedure(procedureCreateInput);
	}
	
	@PutMapping
	public Procedure updateProcedure(@RequestBody @Valid RequestProcedureUpdate request) {
		ProcedureUpdateInput procedureUpdateInput = modelMapper().map(request, ProcedureUpdateInput.class);
		return procedureUseCase.updateProcedure(procedureUpdateInput);
	}
	
	@GetMapping("/{id}")
	public Procedure findProcedureById(@PathVariable("id") Long id) {
		return procedureUseCase.findProcedureById(id);
	}
	
	@GetMapping("/name/{partialName}")
	public List<Procedure> searchByName(@PathVariable("partialName") String partialName) {
		return procedureUseCase.searchProcedureByName(partialName);
	}
	
	@GetMapping("/activeprofessionaltype/{id}")
	public List<Procedure> findActiveProfessionalTypeId(@PathVariable("id") Long id) {
		return procedureUseCase.findActiveProfessionalTypeId(id);
	}	
	
	@GetMapping("/professionaltype/{id}")
	public List<Procedure> findProfessionalTypeId(@PathVariable("id") Long id) {
		return procedureUseCase.findProfessionalTypeId(id);
	}		
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Procedure deleteProcedureById(@PathVariable("id") Long id) {
		return procedureUseCase.deleteProcedureById(id);
	}

}
