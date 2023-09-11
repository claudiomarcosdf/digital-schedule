package br.com.claudio.infra.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestScheduleUpdateStatus {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String status;		
}
