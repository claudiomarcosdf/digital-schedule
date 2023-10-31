package br.com.claudio.common;

import static br.com.claudio.common.PatientConstants.PATIENT1;
import static br.com.claudio.common.ProcedureConstants.PROCEDURE1;
import static br.com.claudio.common.ProcedureConstants.VALID_PROFESSIONALTYPE;
import static br.com.claudio.common.ProfessionalConstants.PROFESSIONAL1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.config.db.schemas.enums.Gender;
import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import br.com.claudio.infra.schedule.dto.PatientResponse;
import br.com.claudio.infra.schedule.dto.ProcedureResponse;
import br.com.claudio.infra.schedule.dto.ProfessionalResponse;
import br.com.claudio.infra.schedule.dto.ProfessionalTypeResponse;
import br.com.claudio.infra.schedule.dto.RequestScheduleCreate;
import br.com.claudio.infra.schedule.dto.ScheduleResponse;

public class ScheduleConstants {
	
	public static final Schedule SCHEDULE1 = new Schedule(1L, LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(1L),
			"teste de novo agendamento médico2", BigDecimal.TEN, VALID_PROFESSIONALTYPE, 
			PROFESSIONAL1, PATIENT1, PROCEDURE1, StatusSchedule.AGENDADO, true, LocalDateTime.now());

	public static final Schedule SCHEDULE2 = new Schedule(1L, LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(1L),
			"teste de novo agendamento médico2", BigDecimal.TEN, VALID_PROFESSIONALTYPE, 
			PROFESSIONAL1, PATIENT1, PROCEDURE1, StatusSchedule.AGENDADO, true, LocalDateTime.now());
	
	// TO RESPONSE
	
	private static final ProfessionalTypeResponse professionalType = new ProfessionalTypeResponse(1L) ;
	private static final ProfessionalResponse professional = new ProfessionalResponse(1L, "Ju", 789542, 30, 0, "6199875632", null);
	private static final PatientResponse patient = new PatientResponse(3L, "Fred", "Frederico...", LocalDate.now(), Gender.MASCULINO, "60184574153", "61988563476", null);
	private static final ProcedureResponse procedure = new ProcedureResponse(1L, "consulta medica", BigDecimal.ZERO);	
	
	
	public static final ScheduleResponse SCHEDUELE_RESPONSE1 = new ScheduleResponse(1L, LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(1L),
			"teste de novo agendamento médico2", BigDecimal.TEN, professionalType, 
			professional, patient, procedure, StatusSchedule.AGENDADO, true, LocalDateTime.now());
	
	public static final ScheduleResponse SCHEDUELE_RESPONSE2 = new ScheduleResponse(1L, LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(1L),
			"teste de novo agendamento médico2", BigDecimal.TEN, professionalType, 
			professional, patient, procedure, StatusSchedule.AGENDADO, true, LocalDateTime.now());	
	
	@SuppressWarnings("serial")
	public static final List<ScheduleResponse> SCHEDULELIST = new ArrayList<>() {
		{
			add(SCHEDUELE_RESPONSE1);
			add(SCHEDUELE_RESPONSE2);
		}
	};	
	
	public static final RequestScheduleCreate SCHEDULE_CREATE = new RequestScheduleCreate(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(1L),
			"descrição", BigDecimal.ZERO, 1L, 1L, 4L, 1L);
	
	public static final RequestScheduleCreate INVALID_SCHEDULE_CREATE = new RequestScheduleCreate(LocalDateTime.now().minusDays(1L), LocalDateTime.now().minusDays(1L),
			"descrição", BigDecimal.ZERO, null, null, 4L, 1L);


}
