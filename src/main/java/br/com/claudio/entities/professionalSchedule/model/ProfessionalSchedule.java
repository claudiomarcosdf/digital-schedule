package br.com.claudio.entities.professionalSchedule.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalSchedule {

	private Long id;
	
	private String monday;
	
	private String tuesday;
	
	private String wednesday;
	
	private String thursday;
	
	private String friday;
	
	private String saturday;
	
	private String sunday;
	
	public LocalTime getHourMorningIni(String value) {
		return LocalTime.parse(value.substring(0, 5));
	}
	
	public LocalTime getHourMorningEnd(String value) {
		return LocalTime.parse(value.substring(6, 11));
	}
	
	public LocalTime getHourAfternoonIni(String value) {
		return LocalTime.parse(value.substring(12, 17));
	}
	
	public LocalTime getHourAfternoonEnd(String value) {
		return LocalTime.parse(value.substring(18, 23));
	}
	
}
