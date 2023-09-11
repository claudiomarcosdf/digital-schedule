package br.com.claudio.usecase.schedule;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreateInput {
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private String description;
	
	private BigDecimal amountPaid = BigDecimal.ZERO;
	
	private Long professionalTypeId;
	
	private Long professionalId;
	
	private Long patientId;
	
	private Long procedureId;
	
	public boolean hasPatient() {
		return this.patientId != null;
	}
	
	public boolean hasProcedure() {
		return this.procedureId != null;
	}	
}
