package br.com.claudio.infra.professionaltype.gateway;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import br.com.claudio.infra.config.db.schemas.ProfessionalTypeSchema;

public class QueryBuilderProfessionalType {
		
	public static Example<ProfessionalTypeSchema> makeQuery(ProfessionalTypeSchema professionalTypeSchema) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withIgnoreCase().withIgnoreNullValues();
				
	    return Example.of(professionalTypeSchema, exampleMatcher);
	}	

}
