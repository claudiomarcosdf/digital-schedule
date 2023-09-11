package br.com.claudio.entities.schedule.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
	
	private Long id;
	
	@NotNull
	private LocalDateTime startDate;
	
	@NotNull
	private LocalDateTime endDate;
	
	private String description;
	
	private BigDecimal amountPaid = BigDecimal.ZERO;
	
	@NotNull
	private ProfessionalType professionalType;
	
	@NotNull
	private Professional professional;
	
	private Patient patient;
	
	private Procedure procedure;
	
	private StatusSchedule status = StatusSchedule.AGENDADO;
	
	private Boolean active = true;
	
	private LocalDateTime createdDate;
}
