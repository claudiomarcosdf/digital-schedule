package br.com.claudio.infra.config.querybuilder;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {
	
	public static Example<Object> makeQuery(Object object) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
				
	    return Example.of(object, exampleMatcher);
	}	

}
