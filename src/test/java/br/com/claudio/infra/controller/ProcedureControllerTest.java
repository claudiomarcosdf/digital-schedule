package br.com.claudio.infra.controller;

import static br.com.claudio.common.ProcedureConstants.INVALID_PROCEDURE;
import static br.com.claudio.common.ProcedureConstants.PROCEDURE1;
import static br.com.claudio.common.ProcedureConstants.PROCEDURECREATEIMPUT;
import static br.com.claudio.common.ProcedureConstants.PROCEDURELIST;
import static br.com.claudio.common.ProcedureConstants.PROCEDURECREATEIMPUT_INACTIVE_PROFESSIONALTYPE;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.infra.procedure.controller.ProcedureController;
import br.com.claudio.usecase.procedure.ProcedureUseCase;

@WebMvcTest(ProcedureController.class)
public class ProcedureControllerTest {
	
	@MockBean
	private ProcedureUseCase procedureUseCase;	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createProcedure_WithValidData_ReturnsCreated() throws Exception {
		when(procedureUseCase.createProcedure(PROCEDURECREATEIMPUT)).thenReturn(PROCEDURE1);
		
		mockMvc.perform(post("/procedures").content(objectMapper.writeValueAsString(PROCEDURECREATEIMPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(PROCEDURE1.getId()))
		.andExpect(jsonPath("$.name").value(PROCEDURE1.getName()));
	}
	
	@Test
	public void createProcedure_WithInvalidData_ReturnsBadRequest() throws Exception {
		Procedure invalidProcedure = INVALID_PROCEDURE;
		
		mockMvc.perform(post("/procedures").content(objectMapper.writeValueAsString(invalidProcedure)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());		
	}	
	
	@Test
	public void createProcedure_WithExistingNameAndProfessionalType_ReturnConflict() throws Exception {
		when(procedureUseCase.createProcedure(any())).thenThrow(new InvalidOperationException(""));
		
		mockMvc.perform(post("/procedures").content(objectMapper.writeValueAsString(PROCEDURECREATEIMPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void createProcedure_WithInactiveProfessionalType_ReturnConflict() throws Exception {
		when(procedureUseCase.createProcedure(any())).thenThrow(new InvalidOperationException(""));
		
		mockMvc.perform(post("/procedures").content(objectMapper.writeValueAsString(PROCEDURECREATEIMPUT_INACTIVE_PROFESSIONALTYPE)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());	
	}	
	
	@Test
	public void getProcedure_ByExistingId_ReturnsProcedure() throws Exception {
		when(procedureUseCase.findProcedureById(1L)).thenReturn(PROCEDURE1);
		
		mockMvc.perform(get("/procedures/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(PROCEDURE1.getId()))
		.andExpect(jsonPath("$.name").value(PROCEDURE1.getName()));
	}
	
	@Test
	public void getProcedure_ByUnExistingId_ReturunsNotFound() throws Exception {
		when(procedureUseCase.findProcedureById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/procedures/99"))
		.andExpect(status().isNotFound());
	}	
	
	@Test
	public void searchProceduresByPartialName_ReturnsProcedureList() throws Exception {
		when(procedureUseCase.searchProcedureByName("name-to-find")).thenReturn(PROCEDURELIST);
		
		mockMvc.perform(get("/procedures/name/name-to-find"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void searchProceduresByPartialName_ReturnsNoProcedures() throws Exception {
		when(procedureUseCase.searchProcedureByName("name-to-find")).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/procedures/name/name-to-find"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void searchProceduresByProfessionalType_ReturnsProcedureList() throws Exception {
		when(procedureUseCase.findProfessionalTypeId(1L)).thenReturn(PROCEDURELIST);
		
		mockMvc.perform(get("/procedures/professionalType/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void searchProceduresByProfessionalType_ReturnsNoProcedures() throws Exception {
		when(procedureUseCase.findProfessionalTypeId(99L)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/procedures/professionalType/99"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deleteProcedure_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/procedures/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deleteProcedure_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(procedureUseCase).deleteProcedureById(99L);
		
		mockMvc.perform(delete("/procedures/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getProcedure_WithInvalidParam_ReturnsBadRequest() throws Exception {
		when(procedureUseCase.findProcedureById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/procedures/w"))
		.andExpect(status().isBadRequest());
	}

}
