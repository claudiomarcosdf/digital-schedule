package br.com.claudio.common;

import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.config.db.schemas.PersonTypeSchema;
import br.com.claudio.usecase.persontype.PersonTypeUseCase.CreateInput;


public class PersonTypeConstants {
	
	public static final PersonTypeSchema PERSONTYPESCHEMA = new PersonTypeSchema(null, "name");
	
	public static final PersonTypeSchema INVALID_PERSONTYPESCHEMA = new PersonTypeSchema(null, "");
	
	public static final PersonTypeSchema PACIENTE = new PersonTypeSchema(1L, "Paciente");
	
	public static final PersonTypeSchema PROFISSIONAL = new PersonTypeSchema(2L, "Profissional");
	
	// Constraints of entity
	
	public static final PersonType PERSONTYPE = new PersonType(1L, "name");
	public static final PersonType NAME1 = new PersonType(1L, "name1");
	public static final PersonType NAME2 = new PersonType(2L, "name2");
	
	@SuppressWarnings("serial")
	public static final List<PersonType> PERSONTYPELIST = new ArrayList<>() {
		{
			add(NAME1);
			add(NAME2);
		}
	};
	 
	// Constraints of Web
	
	public static final CreateInput CREATEINPUT = new CreateInput("name");
	
	@SuppressWarnings("serial")
	public static final List<PersonTypeSchema> PERSONTYPESCHEMALIST = new ArrayList<>() {
		{
			add(PACIENTE);
			add(PROFISSIONAL);
		}
	};	

}
