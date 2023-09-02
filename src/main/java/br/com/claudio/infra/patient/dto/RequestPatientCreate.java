package br.com.claudio.infra.patient.dto;

import br.com.claudio.infra.person.dto.RequestPersonCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPatientCreate {
	
	private String nickName;
	
	@NotNull
	private RequestPersonCreate person;
}
