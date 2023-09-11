package br.com.claudio.infra.professionalSchedule.dto;

import br.com.claudio.infra.config.validation.ValidPeriodOfDay;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProfessionalScheduleUpdate {
	
	@NotNull
	private Long id;
	
	@NotNull
	private Long professionalId;	
	
	@ValidPeriodOfDay(message = "Período de horários inválido para segunda-feira", emptyValue=true)
	private String monday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para terça-feira", emptyValue=true)
	private String tuesday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para quarta-feira", emptyValue=true)
	private String wednesday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para quinta-feira", emptyValue=true)
	private String thursday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para sexta-feira", emptyValue=true)
	private String friday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para sábado", emptyValue=true)
	private String saturday;
	
	@ValidPeriodOfDay(message = "Período de horários inválido para domingo", emptyValue=true)
	private String sunday;			
}
