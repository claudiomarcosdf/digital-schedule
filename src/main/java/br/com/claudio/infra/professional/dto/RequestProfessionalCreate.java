package br.com.claudio.infra.professional.dto;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.person.dto.RequestPersonCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProfessionalCreate {
	
	private String nickName;
	
	@NotNull
	private Integer document;
	
	private Integer durationService;
	
	private Integer intervalService = 0;
	
	@NotNull
	private RequestPersonCreate person;
	
	@NotNull
	private ProfessionalType professionalType;		

}
