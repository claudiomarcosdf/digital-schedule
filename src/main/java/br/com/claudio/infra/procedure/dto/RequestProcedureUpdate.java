package br.com.claudio.infra.procedure.dto;

import java.math.BigDecimal;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProcedureUpdate {
	
	private Long id;	
	
	@NotNull
	private String name;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	@NotNull
	private ProfessionalType professionalType;

}
