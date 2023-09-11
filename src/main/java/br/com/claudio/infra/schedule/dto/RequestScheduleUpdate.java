package br.com.claudio.infra.schedule.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestScheduleUpdate {
	
	@NotNull
	private Long id;
	
	@NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startDate;
	
	@NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endDate;
	
	private String description;
	
	private BigDecimal amountPaid = BigDecimal.ZERO;
	
	@NotNull
	private Long professionalId;
	
	private Long patientId;
	
	private Long procedureId;
	
	private String status;	
}
