package br.com.claudio.usecase.patient;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.patient.gateway.PatientGateway;
import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.usecase.person.PersonCreateInput;
import br.com.claudio.usecase.person.PersonUpdateInput;
import br.com.claudio.usecase.person.PersonUseCase;

@Service
public class PatientUseCase {
	
	private final PatientGateway patientGateway;
	private final PersonUseCase personUseCase;
	
	public PatientUseCase(PatientGateway patientGateway, PersonUseCase personUseCase) {
		this.patientGateway = patientGateway;
		this.personUseCase = personUseCase;
	}

	public Patient createPatient(PatientCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		PersonCreateInput personInput = modelMapper().map(input.getPerson(), PersonCreateInput.class);
		Person person = personUseCase.createPerson(personInput); //person salvo no db
		
		Patient patient = new Patient();
		patient.setNickName(input.getNickName());
		patient.setPerson(person);
		
		return patientGateway.create(patient);
	}
	
	public Patient updatePatient(PatientUpdateInput input) throws ResourceNotFoundException {
		if (input == null) throw new RequiredObjectIsNullException();
		
		Patient patient = this.findPatientById(input.getId()); //pega dados imutáveis
		PersonUpdateInput personInput = modelMapper().map(input.getPerson(), PersonUpdateInput.class);
		
		personInput.setCreatedDate(patient.getPerson().getCreatedDate()); //pega data que não muda		
		
		personUseCase.updatePerson(personInput); //atualiza dados do person	
		
		Patient patientMapped = modelMapper().map(input, Patient.class);
		return patientGateway.update(patientMapped); //atualiza dados do patient
	}
	
	public Patient findPatientById(Long id) throws ResourceNotFoundException {
		return patientGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado!"));
	}
	
	public List<Patient> list(String fullName, String cpf, Integer rg) {
		return patientGateway.list(fullName, cpf, rg);
	}
	
	public List<Patient> searchPatientByName(String fullName) {
		return patientGateway.searchByName(fullName.toLowerCase());
	}	

	public void deletePatient(Long id) throws ResourceNotFoundException {
		var patient = patientGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado!"));
		
		patient.getPerson().setActive(false);
		patientGateway.update(patient);
	}
	

}
