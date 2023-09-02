package br.com.claudio.usecase.patient;

import br.com.claudio.usecase.person.PersonUpdateInput;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateInput {
	
	@NotNull
	private Long id;
	
	private String nickName;
	
	@NotNull
	private PersonUpdateInput person;

}
