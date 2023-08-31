package br.com.claudio.entities.persontype.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonType {
	
	private Long id;
	
	@NotEmpty
	private String name;

	public PersonType(@NotEmpty String name) {
		this.name = name;
	}

}
