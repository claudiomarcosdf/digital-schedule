package br.com.claudio.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.config.db.schemas.enums.Gender;
import br.com.claudio.usecase.patient.PatientCreateInput;
import br.com.claudio.usecase.person.PersonCreateInput;

public class PatientConstants {
	
	public static final PersonType PERSONTYPE_PATIENT = new PersonType(1L, "Paciente");
	public static final PersonType PERSONTYPE_PROFISSIONAL = new PersonType(2L, "Profissional");
	
	private static LocalDate birthDay = LocalDate.parse("1989-08-10");
	private static LocalDateTime createdAt = LocalDateTime.parse("2023-09-02T14:20:59.281337");
	
	public static final Person PERSON_PATIENT = new Person(1L, "fulano", "email@email.com",
			birthDay, "601.845.741-53", 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PATIENT, createdAt);
	
	public static final Patient PATIENT = new Patient(1L, "Paciente Fulano", PERSON_PATIENT);
//	public static final Person PERSON_PROFISSIONAL = new Person(1L, "beltrana", "email@email.com",
//			birthDay, "828.542.741-34", 123, Gender.FEMININO,  "",
//			71939360, "", "", true, PERSONTYPE_PROFISSIONAL, 
//			createdAt);	
//	public static final Patient PROFISSIONAL = new Patient(2L, "Profissional Beltrano", PERSON_PROFISSIONAL);
	
	public static final PersonType INVALID_PERSONTYPE = new PersonType(null, "");
	
	public static final Patient INVALID_PATIENT = new Patient(null, "", null);
	
	public static final Patient INVALID_PATIENT_WITHOUT_PERSON = new Patient(null, "paciente", null);
	
	public static final Person INVALID_PERSON = new Person(null, null, "email-email.com",
			birthDay, null, 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PATIENT, createdAt);
	
	public static final Person INVALID_CPF_PERSON = new Person(null, "fulano", "email@email.com",
			birthDay, "123.456.789-00", 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PATIENT, createdAt);	
	
	public static final Patient VALID_PATIENT_WITH_INVALID_PERSON = new Patient(1L, "name1", INVALID_PERSON);
	
	public static final Patient INVALID_PATIENT_WITH_INVALID_CPF = new Patient(1L, "name1", INVALID_PERSON);
	
	// Constraints of entity
	
	public static final Person PERSON_PATIENT1 = new Person(1L, "fulano", "fulano@gmail.com",
			birthDay, "601.845.741-53", 123, Gender.MASCULINO,  "",
			71939360, "", "", true, PERSONTYPE_PATIENT, createdAt);	
	
	public static final Person PERSON_PATIENT2 = new Person(2L, "Beltrana", "beltrano@email.com",
			birthDay, "828.542.701-34", 333, Gender.FEMININO,  "",
			71939360, "", "", true, PERSONTYPE_PATIENT, createdAt);	
	
	public static final Patient PATIENT1 = new Patient(1L, "name1", PERSON_PATIENT1);
	public static final Patient PATIENT2 = new Patient(2L, "name2", PERSON_PATIENT2);
	
	@SuppressWarnings("serial")
	public static final List<Patient> PATIENTLIST = new ArrayList<>() {
		{
			add(PATIENT1);
			add(PATIENT2);
		}
	};
	 
	// Constraints of Web
	
	public static final PersonCreateInput PERSON_CREATE_INPUT = new PersonCreateInput("fulano", "email@email.com", 
			birthDay, "601.845.741-53", 123, "Masculino",  "",
			71939360, "", "", PERSONTYPE_PATIENT);
	
	public static final PatientCreateInput PATIENT_CREATE_INPUT = new PatientCreateInput("Paciente Fulano", PERSON_CREATE_INPUT);

}
