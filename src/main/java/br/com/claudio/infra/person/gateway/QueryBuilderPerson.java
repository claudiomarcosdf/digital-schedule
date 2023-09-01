package br.com.claudio.infra.person.gateway;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import br.com.claudio.infra.config.db.schemas.PersonSchema;

public class QueryBuilderPerson {
		
	public static Example<PersonSchema> makeQuery(PersonSchema personSchema) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("active").withIgnoreCase().withIgnoreNullValues();
				
	    return Example.of(personSchema, exampleMatcher);
	}	

}
