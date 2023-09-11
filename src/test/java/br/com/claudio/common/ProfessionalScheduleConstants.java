package br.com.claudio.common;

import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;
import br.com.claudio.usecase.professionalSchedule.ProfessionalScheduleCreateInput;

public class ProfessionalScheduleConstants {
	
	public static final ProfessionalSchedule PROFESSIONALSCHEDULE1 = new ProfessionalSchedule(1L,"08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00");
	
	public static final ProfessionalSchedule PROFESSIONALSCHEDULE2 = new ProfessionalSchedule(2L,"08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00");
	
	public static final ProfessionalSchedule INVALID_PROFESSIONALSCHEDULE = 
			new ProfessionalSchedule(null, "A8:00-12:00=13:00-18:00", "A8:00-12:00=13:00-18:00",
			null, null, null, null, null);
	
	@SuppressWarnings("serial")
	List<ProfessionalSchedule> PROFESSIONALSCHEDULE_LIST = new ArrayList<>() {
		{
			add(PROFESSIONALSCHEDULE1);
			add(PROFESSIONALSCHEDULE2);
		}
	};
	
	public static final ProfessionalScheduleCreateInput CREATE_IMPUT = new ProfessionalScheduleCreateInput(1L,"08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00",
		    "08:00-12:00,13:00-18:00"
    );
	

}
