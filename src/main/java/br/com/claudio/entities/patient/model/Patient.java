package br.com.claudio.entities.patient.model;

import br.com.claudio.entities.person.model.Person;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	
	private Long id;

	private String nickName;

	@NotNull
	private Person person;	
}
