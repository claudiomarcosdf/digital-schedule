package br.com.claudio.infra.controller;

import static br.com.claudio.common.ProfessionalScheduleConstants.CREATE_IMPUT;
import static br.com.claudio.common.ProfessionalScheduleConstants.INVALID_PROFESSIONALSCHEDULE;
import static br.com.claudio.common.ProfessionalScheduleConstants.PROFESSIONALSCHEDULE1;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.infra.professionalSchedule.controller.ProfessionalScheduleController;
import br.com.claudio.usecase.professionalSchedule.ProfessionalScheduleUseCase;

@WebMvcTest(ProfessionalScheduleController.class)
public class ProfessionalScheduleControllerTest {
	
	@MockBean
	ProfessionalScheduleUseCase professionalScheduleUseCase;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void createProfessionalSchedule_WithValidData_ReturnsCreated() throws Exception {
		when(professionalScheduleUseCase.createProfessionalSchedule(CREATE_IMPUT)).thenReturn(PROFESSIONALSCHEDULE1);
		
		var result = mockMvc.perform(post("/professionalschedules").content(objectMapper.writeValueAsString(CREATE_IMPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
		.andDo(print())
		.andExpect(jsonPath("$.id").value(PROFESSIONALSCHEDULE1.getId()))
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test void createProfessionalSchedule_WithInvalidData_ReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/professionalschedules").content(objectMapper.writeValueAsString(INVALID_PROFESSIONALSCHEDULE)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void createProfessionalSchedule_ByUnexistingProfessionalId_ReturunsNotFound() throws Exception {
		doThrow(ResourceNotFoundException.class).when(professionalScheduleUseCase).createProfessionalSchedule(CREATE_IMPUT);
		
		mockMvc.perform(post("/professionalschedules").content(objectMapper.writeValueAsString(CREATE_IMPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	

}
