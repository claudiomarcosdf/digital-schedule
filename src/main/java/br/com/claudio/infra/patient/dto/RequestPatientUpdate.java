package br.com.claudio.infra.patient.dto;

import br.com.claudio.infra.person.dto.RequestPersonUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPatientUpdate {
	
	@NotNull
	private Long id;
	
	private String nickName;
	
	@NotNull
	private RequestPersonUpdate person;

}
