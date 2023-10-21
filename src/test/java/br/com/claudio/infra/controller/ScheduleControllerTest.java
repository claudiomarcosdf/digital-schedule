package br.com.claudio.infra.controller;

import static br.com.claudio.common.ScheduleConstants.INVALID_SCHEDULE_CREATE;
import static br.com.claudio.common.ScheduleConstants.SCHEDULE1;
import static br.com.claudio.common.ScheduleConstants.SCHEDULELIST;
import static br.com.claudio.common.ScheduleConstants.SCHEDULE_CREATE;
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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.infra.schedule.controller.ScheduleController;
import br.com.claudio.usecase.schedule.ScheduleCreateInput;
import br.com.claudio.usecase.schedule.ScheduleUseCase;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {
	
	@MockBean
	private ScheduleUseCase scheduleUseCase;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createSchedule_WithValidData_ReturnsCreated() throws Exception {
		when(scheduleUseCase.createSchedule(any(ScheduleCreateInput.class))).thenReturn(SCHEDULE1);
		
		var result = mockMvc.perform(post("/schedules").content(objectMapper.writeValueAsString(SCHEDULE_CREATE)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$").exists())
//		.andDo(print())
		.andExpect(jsonPath("$.id").value(SCHEDULE1.getId()))
		.andExpect(jsonPath("$.patient.person.fullName").value(SCHEDULE1.getPatient().getPerson().getFullName()))
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void createSchedule_WithInvalidData_ReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/schedules").content(objectMapper.writeValueAsString(INVALID_SCHEDULE_CREATE)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isUnprocessableEntity());
	}	
	
//	@Test
//	public void getSchedule_WithExistingId_ReturnsSchedule() throws Exception {
//		when(scheduleUseCase.findScheduleById(1L)).thenReturn();
//		
//		mockMvc.perform(post("/schedules").content(objectMapper.writeValueAsString(INVALID_SCHEDULE_CREATE)).contentType(MediaType.APPLICATION_JSON))		
//		.andExpect(status().isUnprocessableEntity());
//	}	
	
	@Test
	public void listActiveSchedule_WithProfessionalTypeIdAndProfessionalId_ReturnsScheduleResponse() throws Exception {
		when(scheduleUseCase.listActiveSchedules(1L, 1L, "2023-09-30", "2023-09-31")).thenReturn(SCHEDULELIST);	
		
		mockMvc.perform(get("/schedules").param("professionalTypeId", "1").param("professionalId", "1").param("startDate", "2023-09-30").param("endDate", "2023-09-31"))		
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}		
	
	@Test
	public void listActiveSchedule_WithUnexistingProfessionalTypeIdAndProfessionalId_ReturnsEmptyList() throws Exception {
		when(scheduleUseCase.listActiveSchedules(99L, 99L, "2023-09-30", "2023-09-31")).thenReturn(Collections.emptyList());	
		
		mockMvc.perform(get("/schedules").param("professionalTypeId", "99").param("professionalId", "99"))		
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void listActiveSchedule_WithAnyInvalidRequestParam_ReturnsBadRequest() throws Exception {
		when(scheduleUseCase.listActiveSchedules(null, null, null, null)).thenThrow(MethodArgumentTypeMismatchException.class);	
		
		mockMvc.perform(get("/schedules"))		
		.andExpect(status().isBadRequest());
	}	
	
	@Test
	public void deleteSchedule_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/schedules/1"))		
		.andExpect(status().isNoContent());
	}	

	@Test
	public void deleteSchedule_WithUnexistingId_ReturnsBadRequest() throws Exception {
        doThrow(ResourceNotFoundException.class).when(scheduleUseCase).deleteSchedule(99L);
		
		mockMvc.perform(delete("/schedules/99"))		
		.andExpect(status().isNotFound());
	}		

}
