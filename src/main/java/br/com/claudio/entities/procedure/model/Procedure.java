package br.com.claudio.entities.procedure.model;

import java.math.BigDecimal;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {
	
	private Long id;
	
	@NotNull
	private String name;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	private Boolean active = true;
	
	@NotNull
	private ProfessionalType professionalType;	

}
