package br.com.claudio.infra.patient.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.claudio.entities.patient.gateway.PatientGateway;
import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.infra.config.db.repositories.PatientRepository;
import br.com.claudio.infra.config.db.schemas.PatientSchema;
import br.com.claudio.infra.config.db.schemas.PersonSchema;

@Component
public class PatientDatabaseGateway implements PatientGateway {
	
	private final PatientRepository patientRepository;
	
	public PatientDatabaseGateway(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Override
	public Patient create(Patient patient) {
		PatientSchema patientSchema = toPatientSchema(patient);
		return toPatient(patientRepository.save(patientSchema));
	}
	
	@Override
	public Patient update(Patient patient) {
		PatientSchema patientSchema = toPatientSchema(patient);
		return toPatient(patientRepository.save(patientSchema));
	}
	
	@Override
	public Optional<Patient> findById(Long id) {
		return patientRepository.findById(id)
				.map(schema -> toPatient(schema));
	}	

	@Override
	public void delete(Long id) {
		// Não haverá exclusão física
	}

	@Override
	public List<Patient> searchByNickName(String nickName) {
		return patientRepository.findByNickName(nickName);
	}

	@Override
	public List<Patient> list(String fullName, String cpf, Integer rg) {
		PersonSchema personSchema = new PersonSchema();
		personSchema.setFullName(fullName);
		personSchema.setCpf(cpf);
		personSchema.setRg(rg);
		
		PatientSchema patientSchema = new PatientSchema();
		patientSchema.setPerson(personSchema);
		
		Example<PatientSchema> query = QueryBuilderPatient.makeQuery(patientSchema);
		return toPatientList(patientRepository.findAll(query));
	}


	@Override
	public List<Patient> searchByName(String partialName) {
		return toPatientList(patientRepository.searchByName(partialName));
	}
	

	private Patient toPatient(PatientSchema patientSchema) {
		return modelMapper().map(patientSchema, Patient.class);
	}


	private PatientSchema toPatientSchema(Patient patient) {
		return modelMapper().map(patient, PatientSchema.class);
	}	
	
	private List<Patient> toPatientList(List<PatientSchema> listPatientSchema) {
		
		List<Patient> patientList = new ArrayList<>();
		for (PatientSchema patientSchema: listPatientSchema) {
			patientList.add(toPatient(patientSchema));
		}

		return patientList;
	}	

}
