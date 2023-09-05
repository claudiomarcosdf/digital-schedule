package br.com.claudio.infra.controller;

import static br.com.claudio.common.YourBeanConstants.CREATEINPUT;
import static br.com.claudio.common.YourBeanConstants.YOURBEAN;
import static br.com.claudio.common.YourBeanConstants.YOURBEANLIST;
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
import br.com.claudio.entities.yourbean.model.YourBean;
import br.com.claudio.usecase.yourbean.YourBeanUseCase;

@WebMvcTest(YourBeanController.class)
public class DefaultControllerTest {
	
	@MockBean
	private YourBeanUseCase yourUseCase;	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void createYourBean_WithValidData_ReturnsCreated() throws Exception {
		when(yourBeanUseCase.createYourBean(CREATEINPUT)).thenReturn(YOURBEAN);
		
		mockMvc.perform(post("/yourendpoint").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))		
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(YOURBEAN.getId()))
		.andExpect(jsonPath("$.name").value(YOURBEAN.getName()));
	}
	
	@Test
	public void createYourBean_WithInvalidData_ReturnsBadRequest() throws Exception {
		YourBean emptyYourBean = new YourBean(null, null); //com todos os atributos nulos
		YourBean invalidYourBean = new YourBean("");		
	
		mockMvc.perform(post("/yourendpoint").content(objectMapper.writeValueAsString(emptyYourBean)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());
		
		mockMvc.perform(post("/yourendpoint").content(objectMapper.writeValueAsString(invalidYourBean)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnprocessableEntity());		
	}	
	
	@Test
	public void createYourBean_WithExistingName_ReturnConflict() throws Exception {
		when(yourBeanUseCase.createYourBean(any())).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc.perform(post("/yourendpoint").content(objectMapper.writeValueAsString(CREATEINPUT)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());	
	}
	
	@Test
	public void getYourBean_ByExistingId_ReturnsYourBean() throws Exception {
		when(yourBeanUseCase.findYourBeanById(1L)).thenReturn(YOURBEAN);
		
		mockMvc.perform(get("/yourendpoint/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(YOURBEAN.getId()))
		.andExpect(jsonPath("$.name").value(YOURBEAN.getName()));
	}
	
	@Test
	public void getYourBean_ByUnExistingId_ReturunsNotFound() throws Exception {
		when(yourBeanUseCase.findYourBeanById(99L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/yourendpoint/99"))
		.andExpect(status().isNotFound());
	}	
	
	@Test
	public void listYourBean_ReturnsAllYourBeans() throws Exception {
		when(yourBeanUseCase.findAllYourBean()).thenReturn(YOURBEANLIST);
		
		mockMvc.perform(get("/yourendpoint"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	public void listYourBean_ReturnsNoYourBeans() throws Exception {
		when(yourBeanUseCase.findAllYourBean()).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/yourendpoint"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}	
	
	@Test
	public void deleteYourBean_WithExistingId_ReturnsNoContent() throws Exception {
		mockMvc.perform(delete("/yourendpoint/99"))
		.andExpect(status().isNoContent());
	}	
	
	@Test
	public void deleteYourBean_WithUnexistingId_ReturnsNoContent() throws Exception {
		doThrow(new ResourceNotFoundException("")).when(yourBeanUseCase).deleteYourBean(99L);
		
		mockMvc.perform(delete("/yourendpoint/99"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getYourBean_WithInvalidParam_ReturnsBadRequest() throws Exception {
		when(yourBeanUseCase.findYourBeanById(1L)).thenThrow(MethodArgumentTypeMismatchException.class);
		
		mockMvc.perform(get("/yourendpoint/w"))
		.andExpect(status().isBadRequest());
	}

}
