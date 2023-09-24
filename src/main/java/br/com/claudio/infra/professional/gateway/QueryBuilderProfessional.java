package br.com.claudio.infra.professional.gateway;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import br.com.claudio.infra.config.db.schemas.ProfessionalSchema;

public class QueryBuilderProfessional {
		
	public static Example<ProfessionalSchema> makeQuery(ProfessionalSchema professionalSchema) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("person.active").withIgnorePaths("intervalService").withIgnoreCase().withIgnoreNullValues();
				
	    return Example.of(professionalSchema, exampleMatcher);
	}	

}
