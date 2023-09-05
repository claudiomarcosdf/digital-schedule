package br.com.claudio.infra.controller;

import static br.com.claudio.common.PersonTypeConstants.CREATEINPUT;
import static br.com.claudio.common.PersonTypeConstants.PERSONTYPE;
import static br.com.claudio.common.PersonTypeConstants.PERSONTYPELIST;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.persontype.controller.PersonTypeController;
import br.com.claudio.usecase.persontype.PersonTypeUseCase;

@WebMvcTest(PersonTypeController.class)
public class PersonTypeControllerTest {
	@MockBean
	private PersonTypeUseCase personTypeUseCase;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createPersonType_WithValidData_ReturnsCreated() throws Exception {
		when(personTypeUseCase.createPersonType(CREATEINPUT)).thenReturn(PERSONTYPE);
		
		mockMvc.perform(post("/persontypes").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(PERSONTYPE.getId()))
		.andExpect(jsonPath("$.name").value(PERSONTYPE.getName()));
	}
	
	@Test
	public void createPersonType_WithInvalidData_ReturnsBadRequest() throws Exception {
		PersonType emptyPersonType = new PersonType(null, null); //com todos os atributos nulos
		PersonType invalidPersonType = new PersonType("");		
	
		mockMvc.perform(post("/persontypes").content(objectMapper.writeValueAsString(emptyPersonType)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());
		
		mockMvc.perform(post("/persontypes").content(objectMapper.writeValueAsString(invalidPersonType)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());		
	}	
	
	@Test
	public void createPersonType_WithExistingName_ReturnConflict() throws Exception {
		when(personTypeUseCase.createPersonType(any())).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc.perform(post("/persontypes").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());	
	}
	
	@Test
	public void getPersonType_ByExistingId_ReturnsPersonType() throws Exception {
		when(personTypeUseCase.findPersonTypeById(1L)).thenReturn(PERSONTYPE);
		
		mockMvc.perform(get("/persontypes/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(PERSONTYPE.getId()))
		.andExpect(jsonPath("$.name").value(PERSONTYPE.getName()));
	}
	
	@Test
	public void getPersonType_ByUnExistingId_ReturunsNotFound() throws Exception {
		when(personTypeUseCase.findPersonTypeById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/persontypes/99"))
		.andExpect(status().isNotFound());
	}	
	
	@Test
	public void listPersonType_ReturnsAllPersonTypes() throws Exception {
		when(personTypeUseCase.findAllPersonType()).thenReturn(PERSONTYPELIST);
		
		mockMvc.perform(get("/persontypes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void listPersonType_ReturnsNoPersonTypes() throws Exception {
		when(personTypeUseCase.findAllPersonType()).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/persontypes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deletePersonType_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/persontypes/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deletePersonType_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(personTypeUseCase).deletePersonType(99L);
		
		mockMvc.perform(delete("/persontypes/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPersonType_WithInvalidParam_ReturnsBadRequest() throws Exception {
		when(personTypeUseCase.findPersonTypeById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/persontypes/w"))
		.andExpect(status().isBadRequest());
	}
	
}
