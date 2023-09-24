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
		if (value == null) return null;
		if (value != null && value.startsWith("null")) {
			return null;
		} else return LocalTime.parse(value.substring(0, 5)); 
	}
	
	public LocalTime getHourMorningEnd(String value) {
		if (value == null) return null;
		if (value != null && value.startsWith("null")) {
			return null;
		} else return LocalTime.parse(value.substring(6, 11));
	}
	
	public LocalTime getHourAfternoonIni(String value) {
		//08:00-12:00,null  or  null,14:00-18:00

		if (value != null) {
			if (value.endsWith("null")) return null;
			else if (value.startsWith("null")) return LocalTime.parse(value.substring(5, 10));
			else return LocalTime.parse(value.substring(12, 17));
		}
		return null;
	}
	
	public LocalTime getHourAfternoonEnd(String value) {
		//08:00-12:00,null  or  null,14:00-18:00
		if (value != null) {
			if (value.endsWith("null")) return null;
			else if (value.startsWith("null")) return LocalTime.parse(value.substring(11 , 16));
			else return LocalTime.parse(value.substring(18, 23));
		}
		return null;

	}
	
}
