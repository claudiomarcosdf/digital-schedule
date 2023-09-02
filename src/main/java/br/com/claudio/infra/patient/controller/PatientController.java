package br.com.claudio.infra.patient.controller;

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

import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.infra.patient.dto.RequestPatientCreate;
import br.com.claudio.infra.patient.dto.RequestPatientUpdate;
import br.com.claudio.usecase.patient.PatientCreateInput;
import br.com.claudio.usecase.patient.PatientUpdateInput;
import br.com.claudio.usecase.patient.PatientUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	private final PatientUseCase patientUseCase;
	
	public PatientController(PatientUseCase patientUseCase) {
		this.patientUseCase = patientUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Patient createPatient(@RequestBody @Valid RequestPatientCreate request) {
		
		PatientCreateInput patientCreateInput = modelMapper().map(request, PatientCreateInput.class);
		return patientUseCase.createPatient(patientCreateInput);
	}	
	
	@PutMapping
	public Patient updatePatient(@RequestBody @Valid RequestPatientUpdate request) {
		PatientUpdateInput patientUpdateInput = modelMapper().map(request, PatientUpdateInput.class);
		
		return patientUseCase.updatePatient(patientUpdateInput);
	}
	
	@GetMapping("/{id}")
	public Patient findPatientById(@PathVariable("id") Long id) {
		return patientUseCase.findPatientById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Patient> listOneOrAll(@RequestParam(required = false) String fullName, String cpf, Integer rg) {
		return patientUseCase.list(fullName, cpf, rg);
	}
	
	@GetMapping("/name/{partialName}")
	public List<Patient> searchPatient(@PathVariable("partialName") String partialName) {
		return patientUseCase.searchPatientByName(partialName);
	}	
	
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable("id") Long id) {
		patientUseCase.deletePatient(id);
	}
		

}
