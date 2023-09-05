package br.com.claudio.usecase.procedure;

import java.math.BigDecimal;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureCreateInput {
	
	@NotEmpty
	private String name;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	@NotNull
	private ProfessionalType professionalType;		

}
