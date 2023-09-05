package br.com.claudio.infra.controller;

import static br.com.claudio.common.ProfessionalConstants.INVALID_PERSON_PROFESSIONAL;
import static br.com.claudio.common.ProfessionalConstants.INVALID_PROFESSIONAL;
import static br.com.claudio.common.ProfessionalConstants.INVALID_PROFESSIONAL_WITH_INVALID_CPF;
import static br.com.claudio.common.ProfessionalConstants.PROFESSIONAL1;
import static br.com.claudio.common.ProfessionalConstants.PROFESSIONALLIST;
import static br.com.claudio.common.ProfessionalConstants.PROFESSIONAL_CREATE_INPUT;
import static br.com.claudio.common.ProfessionalConstants.VALID_PROFESSIONAL_WITH_INVALID_PERSON;
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
import br.com.claudio.infra.professional.controller.ProfessionalController;
import br.com.claudio.usecase.professional.ProfessionalCreateInput;
import br.com.claudio.usecase.professional.ProfessionalUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@WebMvcTest(ProfessionalController.class)
public class ProfessionalControllerTest {
	
	@MockBean
	private ProfessionalUseCase professionalUseCase;	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createProfessional_WithValidData_ReturnsCreated() throws Exception {
		when(professionalUseCase.createProfessional(any(ProfessionalCreateInput.class))).thenReturn(PROFESSIONAL1);
		
		var result = mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(PROFESSIONAL_CREATE_INPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
		.andDo(print())
		.andExpect(jsonPath("$.id").value(PROFESSIONAL1.getId()))
		.andExpect(jsonPath("$.person.fullName").value(PROFESSIONAL1.getPerson().getFullName()))
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void createProfessional_WithInvalidData_ReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(INVALID_PROFESSIONAL)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());
		
		mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(INVALID_PROFESSIONAL)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());	
	}
	
	@Test
	public void createProfessional_WithInvalidPerson_ReturnsBadRequest() throws Exception {
		//Person person = new Person();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(INVALID_PERSON_PROFESSIONAL);
		
		ConstraintViolationException cve = new ConstraintViolationException(constraintViolations);
		
		doThrow(cve).when(professionalUseCase).createProfessional(any(ProfessionalCreateInput.class));		
		
		mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(VALID_PROFESSIONAL_WITH_INVALID_PERSON)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void createProfessional_WithExistingFullName_ReturnsConflict() throws Exception {
		when(professionalUseCase.createProfessional(any())).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(PROFESSIONAL_CREATE_INPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());	
	}
	
	@Test
	public void createProfessional_WithInvalidCpf_ReturnsBadRequest() throws Exception {
		when(professionalUseCase.createProfessional(any())).thenThrow(CpfInvalidException.class);
		
		mockMvc.perform(post("/professionals").content(objectMapper.writeValueAsString(INVALID_PROFESSIONAL_WITH_INVALID_CPF)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProfessional_ByExistingId_ReturnsProfessional() throws Exception {
		when(professionalUseCase.findProfessionalById(1L)).thenReturn(PROFESSIONAL1);
		
		mockMvc.perform(get("/professionals/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(PROFESSIONAL1.getId()))
		.andExpect(jsonPath("$.nickName").value(PROFESSIONAL1.getNickName()));
	}
	
	@Test
	public void getProfessional_ByUnExistingId_ReturunsNotFound() throws Exception {
		when(professionalUseCase.findProfessionalById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/professionals/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void listProfessionals_ByPersonalTypeId_ReturunsProfissional() throws Exception {
		when(professionalUseCase.findByProfessionalTypeId(1L)).thenReturn(PROFESSIONALLIST);
		
		mockMvc.perform(get("/professionals/professionaltype/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}		
	
	@Test
	public void listProfessionals_ByPersonalTypeId_ReturunsEmptyList() throws Exception {
		when(professionalUseCase.findByProfessionalTypeId(99L)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/professionals/professionaltype/99"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
//	@Test
//	public void listProfessional_ReturnsAllProfessionals() throws Exception {
//		when(professionalUseCase.findAllProfessional()).thenReturn(PROFESSIONAL1LIST);
//		
//		mockMvc.perform(get("/professionals"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$", hasSize(2)));
//	}
	
	@Test
	public void listProfessional_ReturnsNoProfessionals() throws Exception {
		when(professionalUseCase.list(null, null, null)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/professionals"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deleteProfessional_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/professionals/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deleteProfessional_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(professionalUseCase).deleteProfessional(99L);
		
		mockMvc.perform(delete("/professionals/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getAndDeleteProfessional_WithAnyInvalidRequestParam_ReturnsBadRequest() throws Exception {
		when(professionalUseCase.findProfessionalById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/professionals/w"))
		.andExpect(status().isBadRequest());
		
		mockMvc.perform(delete("/professionals/w"))
		.andExpect(status().isBadRequest());		
	}

}
