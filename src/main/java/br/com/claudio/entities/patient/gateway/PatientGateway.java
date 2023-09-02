package br.com.claudio.entities.patient.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.patient.model.Patient;

public interface PatientGateway {
	
	Patient create(Patient patient);
	
	Patient update(Patient patient);
	
	void delete(Long id);
	
	List<Patient> searchByNickName(String nickName);	
	
	Optional<Patient> findById(Long id);
	
	List<Patient> list(String fullName, String cpf, Integer rg);
	
	List<Patient> searchByName(String partialName);	

}
