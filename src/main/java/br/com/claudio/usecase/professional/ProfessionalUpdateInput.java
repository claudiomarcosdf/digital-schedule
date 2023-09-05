package br.com.claudio.usecase.professional;

import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalUpdateInput {
	
	private Long id;
	
	private String nickName;
	
	@NotNull
	private Integer document;
	
	private Integer durationService;
	
	private Integer intervalService;
	
	@NotNull
	private Person person;
	
	@NotNull
	private ProfessionalType professionalType;		

}
