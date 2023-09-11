package br.com.claudio.infra.controller;

import static br.com.claudio.common.ProfessionalTypeConstants.CREATEINPUT;
import static br.com.claudio.common.ProfessionalTypeConstants.INVALID_PROFESSIONALTYPE;
import static br.com.claudio.common.ProfessionalTypeConstants.PROFESSIONAL1;
import static br.com.claudio.common.ProfessionalTypeConstants.PROFESSIONALTYPELIST;
import static br.com.claudio.common.ProfessionalTypeConstants.UPDATEINPUT;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.professionaltype.controller.ProfessionalTypeController;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase;

@WebMvcTest(ProfessionalTypeController.class)
public class ProfessionalTypeControllerTest {
	
	@MockBean
	private ProfessionalTypeUseCase professionalTypeUseCase;	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createProfessionalType_WithValidData_ReturnsCreated() throws Exception {
		when(professionalTypeUseCase.createProfessionalType(CREATEINPUT)).thenReturn(PROFESSIONAL1);
		
		mockMvc.perform(post("/professionaltypes").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(PROFESSIONAL1.getId()))
		.andExpect(jsonPath("$.name").value(PROFESSIONAL1.getName()));
	}
	
	@Test
	public void createProfessionalType_WithInvalidData_ReturnsBadRequest() throws Exception {
		ProfessionalType invalidProfessionalType = INVALID_PROFESSIONALTYPE;
		
		mockMvc.perform(post("/professionaltypes").content(objectMapper.writeValueAsString(invalidProfessionalType)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());		
	}	
	
	@Test
	public void createProfessionalType_WithExistingName_ReturnConflict() throws Exception {
		when(professionalTypeUseCase.createProfessionalType(any())).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc.perform(post("/professionaltypes").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());	
	}
	
	@Test
	public void updateProfessionalType_WithValidData_ReturnsOk() throws Exception {
		when(professionalTypeUseCase.updateProfessionalType(UPDATEINPUT)).thenReturn(PROFESSIONAL1);
		
		mockMvc.perform(put("/professionaltypes").content(objectMapper.writeValueAsString(UPDATEINPUT))
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void updateProfessionalType_ByUnexistingId_ReturnsNotFound() throws Exception {
		when(professionalTypeUseCase.updateProfessionalType(UPDATEINPUT)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(put("/professionaltypes").content(objectMapper.writeValueAsString(UPDATEINPUT))
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isNotFound());
	}	
	
	@Test
	public void getProfessionalType_ByExistingId_ReturnsProfessionalType() throws Exception {
		when(professionalTypeUseCase.findProfessionalTypeById(1L)).thenReturn(PROFESSIONAL1);
		
		mockMvc.perform(get("/professionaltypes/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(PROFESSIONAL1.getId()))
		.andExpect(jsonPath("$.name").value(PROFESSIONAL1.getName()));
	}
	
	@Test
	public void getProfessionalType_ByUnexistingId_ReturunsNotFound() throws Exception {
		when(professionalTypeUseCase.findProfessionalTypeById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/professionaltypes/99"))
		.andExpect(status().isNotFound());
	}	
	
	@Test
	public void listProfessionalType_ReturnsAllActiveProfessionalTypes() throws Exception {
		Boolean active = true;
		when(professionalTypeUseCase.findAllProfessionalType(active)).thenReturn(PROFESSIONALTYPELIST);
		
		mockMvc.perform(get("/professionaltypes").param("active", "true"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void listProfessionalType_ReturnsNoProfessionalTypes() throws Exception {
		when(professionalTypeUseCase.findAllProfessionalType(true)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/professionaltypes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deleteProfessionalType_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/professionaltypes/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deleteProfessionalType_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(professionalTypeUseCase).deleteProfessionalType(99L);
		
		mockMvc.perform(delete("/professionaltypes/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getProfessionalType_WithInvalidParam_ReturnsBadRequest() throws Exception {
		when(professionalTypeUseCase.findProfessionalTypeById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/professionaltypes/w"))
		.andExpect(status().isBadRequest());
	}

}
