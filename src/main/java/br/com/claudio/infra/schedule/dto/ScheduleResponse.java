package br.com.claudio.infra.schedule.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
	
	private Long id;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private String description;
	
	private BigDecimal amountPaid;
	
	private ProfessionalTypeResponse professionalType;
	
	private ProfessionalResponse professional;
	
	private PatientResponse patient;
	
	private ProcedureResponse procedure;
	
	private StatusSchedule status;
	
	private Boolean active;	
	
	private LocalDateTime createdDate;
}

