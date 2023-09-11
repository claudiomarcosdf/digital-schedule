package br.com.claudio.usecase.professionalSchedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalScheduleUpdateInput {
	
	private Long id;
	
	@NotNull
	private Long professionalId;	
	
	private String monday;
	
	private String tuesday;
	
	private String wednesday;
	
	private String thursday;
	
	private String friday;
	
	private String saturday;
	
	private String sunday;		

}
