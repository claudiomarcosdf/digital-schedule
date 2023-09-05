package br.com.claudio.infra.config.db.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.infra.config.db.schemas.PatientSchema;

public interface PatientRepository extends JpaRepository<PatientSchema, Long> {
	
	<S extends PatientSchema> List<S> findAll(Example<S> query);
	
	@Query("SELECT patient FROM PatientSchema patient WHERE " +
			  "patient.person.active = true " +
			  "AND LOWER(patient.person.fullName) LIKE %:partialName% "+
			  "ORDER BY patient.person.fullName"
			  )
	List<PatientSchema> searchByName(String partialName);

	List<Patient> findByNickName(String nickName);	

}
