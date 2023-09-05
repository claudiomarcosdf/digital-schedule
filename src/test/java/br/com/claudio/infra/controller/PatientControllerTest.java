package br.com.claudio.infra.controller;

import static br.com.claudio.common.PatientConstants.INVALID_PATIENT;
import static br.com.claudio.common.PatientConstants.INVALID_PATIENT_WITHOUT_PERSON;
import static br.com.claudio.common.PatientConstants.INVALID_PATIENT_WITH_INVALID_CPF;
import static br.com.claudio.common.PatientConstants.INVALID_PERSON;
import static br.com.claudio.common.PatientConstants.PATIENT;
import static br.com.claudio.common.PatientConstants.PATIENT_CREATE_INPUT;
import static br.com.claudio.common.PatientConstants.VALID_PATIENT_WITH_INVALID_PERSON;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudio.entities.exception.CpfInvalidException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.infra.patient.controller.PatientController;
import br.com.claudio.usecase.patient.PatientCreateInput;
import br.com.claudio.usecase.patient.PatientUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {
	
	@MockBean
	private PatientUseCase patientUseCase;	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createPatient_WithValidData_ReturnsCreated() throws Exception {
		when(patientUseCase.createPatient(any(PatientCreateInput.class))).thenReturn(PATIENT);
		
		var result = mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(PATIENT_CREATE_INPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
		.andDo(print())
		.andExpect(jsonPath("$.id").value(PATIENT.getId()))
		.andExpect(jsonPath("$.person.fullName").value(PATIENT.getPerson().getFullName()))
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void createPatient_WithInvalidData_ReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(INVALID_PATIENT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());
		
		mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(INVALID_PATIENT_WITHOUT_PERSON)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());	
	}
	
	@Test
	public void createPatient_WithInvalidPerson_ReturnsBadRequest() throws Exception {
		//Person person = new Person();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(INVALID_PERSON);
		
		ConstraintViolationException cve = new ConstraintViolationException(constraintViolations);
		
		doThrow(cve).when(patientUseCase).createPatient(any(PatientCreateInput.class));		
		
		mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(VALID_PATIENT_WITH_INVALID_PERSON)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void createPatient_WithExistingFullName_ReturnsConflict() throws Exception {
		when(patientUseCase.createPatient(any())).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(PATIENT_CREATE_INPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());	
	}
	
	@Test
	public void createPatient_WithInvalidCpf_ReturnsBadRequest() throws Exception {
		when(patientUseCase.createPatient(any())).thenThrow(CpfInvalidException.class);
		
		mockMvc.perform(post("/patients").content(objectMapper.writeValueAsString(INVALID_PATIENT_WITH_INVALID_CPF)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPatient_ByExistingId_ReturnsPatient() throws Exception {
		when(patientUseCase.findPatientById(1L)).thenReturn(PATIENT);
		
		mockMvc.perform(get("/patients/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(PATIENT.getId()))
		.andExpect(jsonPath("$.nickName").value(PATIENT.getNickName()));
	}
	
	@Test
	public void getPatient_ByUnExistingId_ReturunsNotFound() throws Exception {
		when(patientUseCase.findPatientById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/patients/99"))
		.andExpect(status().isNotFound());
	}	
	
//	@Test
//	public void listPatient_ReturnsAllPatients() throws Exception {
//		when(patientUseCase.findAllPatient()).thenReturn(PATIENTLIST);
//		
//		mockMvc.perform(get("/patients"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$", hasSize(2)));
//	}
	
	@Test
	public void listPatient_ReturnsNoPatients() throws Exception {
		when(patientUseCase.list(null, null, null)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/patients"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deletePatient_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/patients/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deletePatient_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(patientUseCase).deletePatient(99L);
		
		mockMvc.perform(delete("/patients/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getAndDeletePatient_WithAnyInvalidRequestParam_ReturnsBadRequest() throws Exception {
		when(patientUseCase.findPatientById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/patients/w"))
		.andExpect(status().isBadRequest());
		
		mockMvc.perform(delete("/patients/w"))
		.andExpect(status().isBadRequest());		
	}

}
