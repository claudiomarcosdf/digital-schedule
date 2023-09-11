package br.com.claudio.usecase.schedule;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdateInput {
	
	private Long id;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private String description;
	
	private BigDecimal amountPaid = BigDecimal.ZERO;
	
	private Long professionalId;
	
	private Long patientId;
	
	private Long procedureId;
	
	private String status;
	
	private Boolean active;
	
	public StatusSchedule getStatus() {
		return StatusSchedule.valueOf(this.status.toUpperCase());
	}
	
	public boolean hasPatient() {
		return this.patientId != null;
	}
	
	public boolean hasProcedure() {
		return this.procedureId != null;
	}		

}
