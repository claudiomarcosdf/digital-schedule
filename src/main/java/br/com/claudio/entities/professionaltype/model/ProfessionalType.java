package br.com.claudio.entities.professionaltype.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalType {
	
	private Long id;
	
	@NotEmpty
	private String name;
	
	private Boolean active;
	
	public ProfessionalType(@NotEmpty String name) {
		this.name = name;
	}	

}
