package br.com.claudio.infra.patient.gateway;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import br.com.claudio.infra.config.db.schemas.PatientSchema;

public class QueryBuilderPatient {
		
	public static Example<PatientSchema> makeQuery(PatientSchema patientSchema) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("person.active").withIgnoreCase().withIgnoreNullValues();
				
	    return Example.of(patientSchema, exampleMatcher);
	}	

}
