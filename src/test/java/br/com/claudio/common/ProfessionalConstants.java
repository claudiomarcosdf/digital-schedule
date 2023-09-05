package br.com.claudio.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.config.db.schemas.enums.Gender;
import br.com.claudio.usecase.person.PersonCreateInput;
import br.com.claudio.usecase.professional.ProfessionalCreateInput;

public class ProfessionalConstants {
	public static final PersonType PERSONTYPE_PROFISSIONAL = new PersonType(2L, "Profissional");
	
	private static LocalDate birthDay = LocalDate.parse("1989-08-10");
	private static LocalDateTime createdAt = LocalDateTime.parse("2023-09-02T14:20:59.281337");	
	
	public static final Person PERSON_PROFESSIONAL = new Person(1L, "fulano", "email@email.com",
			birthDay, "601.845.741-53", 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PROFISSIONAL, createdAt);	
	
	public static final Person INVALID_PERSON_PROFESSIONAL = new Person(1L, "", "email-email.com",
			birthDay, null, 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PROFISSIONAL, createdAt);	
	
	public static final ProfessionalType PROFESSIONALTYPE_PROFISSIONAL = new ProfessionalType(1L, "Medico", true);

	public static final Professional INVALID_PROFESSIONAL = new Professional(null, "", null, 30, 0, INVALID_PERSON_PROFESSIONAL, PROFESSIONALTYPE_PROFISSIONAL);
	
	
	public static final Professional VALID_PROFESSIONAL_WITH_INVALID_PERSON = new Professional(1L, "name1", 3345, 30, 0, INVALID_PERSON_PROFESSIONAL, PROFESSIONALTYPE_PROFISSIONAL);
	
	public static final Professional INVALID_PROFESSIONAL_WITH_INVALID_CPF = new Professional(1L, "name1", 12345, 30, 0, INVALID_PERSON_PROFESSIONAL, PROFESSIONALTYPE_PROFISSIONAL);	
	
	public static final Professional PROFESSIONAL1 = new Professional(1L, "Dr Julio", 50998, 30, 0, PERSON_PROFESSIONAL, PROFESSIONALTYPE_PROFISSIONAL); 
	public static final Professional PROFESSIONAL2 = new Professional(2L, "Dra Tereza", 51009, 30, 0, PERSON_PROFESSIONAL, PROFESSIONALTYPE_PROFISSIONAL);
	
	@SuppressWarnings("serial")
	public static final List<Professional> PROFESSIONALLIST = new ArrayList<>() {
		{
			add(PROFESSIONAL1);
			add(PROFESSIONAL2);
		}
	};
	
	public static final PersonCreateInput PERSON_CREATE_INPUT = new PersonCreateInput("fulano", "email@email.com", 
			birthDay, "601.845.741-53", 123, "Masculino",  "",
			71939360, "", "", PERSONTYPE_PROFISSIONAL);
	
	public static final ProfessionalCreateInput PROFESSIONAL_CREATE_INPUT = new ProfessionalCreateInput("Medico Fulano", 50888, 30, 0, PERSON_CREATE_INPUT, PROFESSIONALTYPE_PROFISSIONAL);	
	 	

}
