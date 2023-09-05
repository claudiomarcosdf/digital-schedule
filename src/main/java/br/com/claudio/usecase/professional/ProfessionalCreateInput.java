package br.com.claudio.usecase.professional;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.usecase.person.PersonCreateInput;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalCreateInput {
	
	private String nickName;
	
	@NotNull
	private Integer document;
	
	private Integer durationService;
	
	private Integer intervalService;
	
	@NotNull
	private PersonCreateInput person;
	
	@NotNull
	private ProfessionalType professionalType;	

}
