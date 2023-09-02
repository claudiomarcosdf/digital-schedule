package br.com.claudio.usecase.patient;

import br.com.claudio.usecase.person.PersonCreateInput;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateInput {
	
	private String nickName;
	
	@NotNull
    private PersonCreateInput person;
}
