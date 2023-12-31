package br.com.claudio.usecase.schedule;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.claudio.DigitalScheduleApplication;
import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.patient.model.Patient;
import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.entities.professionalSchedule.model.ProfessionalSchedule;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.entities.schedule.gateway.ScheduleGateway;
import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import br.com.claudio.infra.config.whatsapp.WhatsappService;
import br.com.claudio.infra.config.whatsapp.config.WhatsappProperties;
import br.com.claudio.infra.config.whatsapp.dto.instance.InstanceInfoResponse;
import br.com.claudio.infra.schedule.dto.ScheduleResponse;
import br.com.claudio.usecase.patient.PatientUseCase;
import br.com.claudio.usecase.procedure.ProcedureUseCase;
import br.com.claudio.usecase.professional.ProfessionalUseCase;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase;

@Service
public class ScheduleUseCase {
	
	private static Logger logger = LoggerFactory.getLogger(DigitalScheduleApplication.class);
	
	private final ScheduleGateway scheduleGateway;
	
	private final ProfessionalTypeUseCase professionalTypeUseCase;
	private final ProfessionalUseCase professionalUseCase;
	private final PatientUseCase patientUseCase;
	private final ProcedureUseCase procedureUseCase;
	
	private final WhatsappProperties whatsappProperties;
	private final WhatsappService whatsappService;
	
	public ScheduleUseCase(ScheduleGateway scheduleGateway, ProfessionalTypeUseCase professionalTypeUseCase,
			ProfessionalUseCase professionalUseCase, PatientUseCase patientUseCase, ProcedureUseCase procedureUseCase, WhatsappService whatsappService, WhatsappProperties whatsappProperties) {
		this.scheduleGateway = scheduleGateway;
		this.professionalTypeUseCase = professionalTypeUseCase;
		this.professionalUseCase = professionalUseCase;
		this.patientUseCase = patientUseCase;
		this.procedureUseCase = procedureUseCase;
		this.whatsappProperties = whatsappProperties;
		this.whatsappService = whatsappService;
	}

	public Schedule createSchedule(ScheduleCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		if (input.getEndDate().isBefore(input.getStartDate())) throw new InvalidOperationException("A data final dever ser maior que a data inicial!");
		
		ProfessionalType professionalType = professionalTypeUseCase.findProfessionalTypeById(input.getProfessionalTypeId());

		Professional professional = professionalUseCase.findProfessionalById(input.getProfessionalId());
		
		//podem vir nulos
		Patient patient = null;
		if (input.hasPatient()) patient = patientUseCase.findPatientById(input.getPatientId());
		
		Procedure procedure = null;
		if (input.hasProcedure()) procedure = procedureUseCase.findProcedureById(input.getProcedureId());
		
		//Checa os horário do profissional apenas para agendamentos
		if (patient != null && procedure != null) {
			
			if (professional.hasSchedule()) checkIfProfessionalHasAvailableTime(professional, input.getStartDate());
			else throw new InvalidOperationException("O profissional não possui agenda pré-definida!");
		}
		
		//Checa se existe afastamento do profissional por motivo de congresso, curso etc
		if (scheduleGateway.existsEventsByDate(professionalType.getId(), professional.getId(), 
				input.getStartDate())) 
			throw new InvalidOperationException("Profissional indisponível para esta data!");
		
		Schedule schedule = modelMapper().map(input, Schedule.class);
		schedule.setProfessionalType(professionalType);
		schedule.setProfessional(professional);
		schedule.setPatient(patient);
		schedule.setProcedure(procedure);
		if (patient == null) schedule.setStatus(StatusSchedule.EVENTO);
		
		Schedule scheduleCreated = scheduleGateway.create(schedule);
		sendScheduleMessage(scheduleCreated);
		
		return scheduleCreated;
	}
	
	public Schedule updateSchedule(ScheduleUpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		Schedule scheduleFinded = this.findScheduleById(input.getId());
		
		//Não altera o professinalType 
		
		Professional professional = professionalUseCase.findProfessionalById(input.getProfessionalId());
		
		Patient patient = null;
		if (input.hasPatient()) patient = patientUseCase.findPatientById(input.getPatientId());
		
		Procedure procedure = null;
		if (input.hasProcedure()) procedure = procedureUseCase.findProcedureById(input.getProcedureId());
		
		//Checa os horário do profissional apenas para agendamentos
		if (patient != null && procedure != null) {
			if (professional.hasSchedule()) checkIfProfessionalHasAvailableTime(professional, input.getStartDate());
			else throw new InvalidOperationException("O profissional não possui agenda pré-definida!");	
		}
		
		//Checa se existe afastamento do profissional por motivo de congresso, curso etc
		if (scheduleGateway.existsEventsByDate(scheduleFinded.getProfessionalType().getId(), professional.getId(), 
				input.getStartDate())) 
			throw new InvalidOperationException("Profissional indisponível para esta data!");		
		
		Boolean active = true;
		if (input.getActive() == null) active = scheduleFinded.getActive();
		else active = input.getActive();
		
		Schedule scheduleMapped = modelMapper().map(input, Schedule.class);
		scheduleMapped.setProfessionalType(scheduleFinded.getProfessionalType());
		scheduleMapped.setProfessional(professional);
		scheduleMapped.setPatient(patient);
		scheduleMapped.setProcedure(procedure);
		scheduleMapped.setCreatedDate(scheduleFinded.getCreatedDate());
		scheduleMapped.setActive(active);
		
		Schedule schedule = modelMapper().map(scheduleMapped, Schedule.class);

		
		Schedule scheduleUpdated = scheduleGateway.update(schedule);
		
		//se agenda ativa e status agendado, pode haver alteração nas datas, logo reenviar msg;
		if ((scheduleUpdated.getStatus().name() == "AGENDADO" || scheduleUpdated.getStatus().name() == "EVENTO") 
				&& scheduleUpdated.getActive() == true) {
			whatsappService.sendSingleMessage(scheduleUpdated);
		}
		
		return scheduleUpdated;
	}
	
	public Schedule findScheduleById(Long id) { 
		return scheduleGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado!"));
	}

	private void checkIfProfessionalHasAvailableTime(Professional professional, LocalDateTime startDateTime) {
		Boolean valid = false;
		ProfessionalSchedule professionalSchedule = professional.getProfessionalSchedule();
		
		LocalDate startDate = startDateTime.toLocalDate();
		LocalTime startTime = startDateTime.toLocalTime();
		//pegar o dia da semana da data acima
		DayOfWeek dayOfWeek = startDate.getDayOfWeek(); // result = dayOfWeek.TUESDAY
		//pegar o horário
		
		LocalTime hourMorningIni = null;
		LocalTime hourMorningEnd = null;
		LocalTime hourAfternoonIni = null;
		LocalTime hourAfternoonEnd = null;
		
		switch (dayOfWeek) {
		case SUNDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getSunday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getSunday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getSunday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getSunday());
			break;
		}
		case MONDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getMonday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getMonday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getMonday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getMonday());
			break;
		}	
		case TUESDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getTuesday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getTuesday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getTuesday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getTuesday());
			break;
		}	
		case WEDNESDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getWednesday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getWednesday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getWednesday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getWednesday());
			break;
		}
		case THURSDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getThursday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getThursday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getThursday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getThursday());
			break;
		}	
		case FRIDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getFriday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getFriday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getFriday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getFriday());
			break;
		}	
		case SATURDAY: {
			hourMorningIni = professionalSchedule.getHourMorningIni(professionalSchedule.getSaturday());
			hourMorningEnd = professionalSchedule.getHourMorningEnd(professionalSchedule.getSaturday());
			hourAfternoonIni = professionalSchedule.getHourAfternoonIni(professionalSchedule.getSaturday());
			hourAfternoonEnd = professionalSchedule.getHourAfternoonEnd(professionalSchedule.getSaturday());
			break;
		}			
		default:
			break;
		}
		
		if (hourMorningIni != null && hourMorningEnd != null) {

			if (startTime.equals(hourMorningIni) 
					|| (startTime.isAfter(hourMorningIni) && startTime.isBefore(hourMorningEnd)) ) {
				valid = true;
			}			
		} 
		if (hourAfternoonIni != null && hourAfternoonEnd != null) {

			if (startTime.equals(hourAfternoonIni) 
					|| (startTime.isAfter(hourAfternoonIni) && startTime.isBefore(hourAfternoonEnd)) ) {
				valid = true;
			}			
		}
		
//		if (startTime.equals(hourMorningIni) 
//				|| (startTime.isAfter(hourMorningIni) && startTime.isBefore(hourMorningEnd)) ) {
//			valid = true;
//		} else if (startTime.equals(hourAfternoonIni) 
//				|| (startTime.isAfter(hourAfternoonIni) && startTime.isBefore(hourAfternoonEnd)) ) {
//			valid = true;
//		}
		
		if (!valid) throw new InvalidOperationException("Profissional não disponível para o horário agendado!");
		
	}


	public void deleteSchedule(Long id) {
		Schedule schedule = this.findScheduleById(id);
        schedule.setActive(false);
		scheduleGateway.update(schedule);		
	}
	
	public List<ScheduleResponse> listActiveSchedules(Long professionalTypeId, Long professionalId, String startDate, String endDate) {
		if (professionalTypeId == null || professionalId == null || startDate == null || endDate == null) throw new InvalidOperationException("Parâmetros inválidos ou incompletos");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		
		try {
			startDateTime = LocalDateTime.parse(startDate+" 00:00", formatter);
			endDateTime = LocalDateTime.parse(endDate+" 23:59", formatter);
		} catch (Exception e) {
			throw new InvalidOperationException("O período informado é inválido!");
		}
		
		
		return toScheduleResponse(scheduleGateway.listActiveSchedules(professionalTypeId, professionalId, startDateTime, endDateTime));
	}
	
	private List<ScheduleResponse> getActiveSchedulesByDate(String startDate) {
		if (startDate == null) throw new InvalidOperationException("Data inválida!");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		
		try {
			startDateTime = LocalDateTime.parse(startDate+" 00:00", formatter);
			endDateTime = LocalDateTime.parse(startDate+" 23:59", formatter);
		} catch (Exception e) {
			throw new InvalidOperationException("Data de agendamento inválida!");
		}
		
		
		return toScheduleResponse(scheduleGateway.getSchedulesByDate(startDateTime, endDateTime));
	}	

	private List<ScheduleResponse> toScheduleResponse(List<Schedule> listActiveSchedules) {
		List<ScheduleResponse> listScheduleResponse = new ArrayList<>();
		
		for(Schedule schedule: listActiveSchedules) {
			ScheduleResponse scheculeResponse = modelMapper().map(schedule, ScheduleResponse.class);
			scheculeResponse.getProfessional().setPhone(schedule.getProfessional().getPerson().getPhone());
			scheculeResponse.getProfessional().setPhone2(schedule.getProfessional().getPerson().getPhone2());
			
			if (scheculeResponse.getPatient() != null) {
				scheculeResponse.getPatient().setNickName(schedule.getPatient().getNickName());
				scheculeResponse.getPatient().setFullName(schedule.getPatient().getPerson().getFullName());
				scheculeResponse.getPatient().setBirthDay(schedule.getPatient().getPerson().getBirthDay());
				scheculeResponse.getPatient().setGender(schedule.getPatient().getPerson().getGender());
				scheculeResponse.getPatient().setCpf(schedule.getPatient().getPerson().getCpf());
				scheculeResponse.getPatient().setPhone(schedule.getPatient().getPerson().getPhone());
				scheculeResponse.getPatient().setPhone2(schedule.getPatient().getPerson().getPhone2());
			}
			
            listScheduleResponse.add(scheculeResponse);
		}
		
		return listScheduleResponse;
	}
	
	/**
	 * SEND AN SCHEDULE MESSAGE VIA WHATSAPP
	 * @param schedule
	 */
	
	private void sendScheduleMessage(Schedule schedule) {
		if (!whatsappProperties.useWhatsapp()) return;
				
		try {
			InstanceInfoResponse instanceInfo = whatsappService.getInstanceInfo();
			//Só envia mensagem caso haja paciente (não seja evento)
			if (schedule.getPatient() != null) whatsappService.sendSingleMessage(schedule);
		} catch (Exception e) {
			logger.error("Whatsapp não iniciado - instância não criada");
		}
		
	}
	
	/**
	 * SEND SCHEDULE CONFIRMATION MESSAGES VIA WHATSAPP
	 * @param schedule
	 */	
	
	public Boolean sendConfirmationMessage(String date) {
		Boolean error = false;
		
		if (whatsappProperties.useWhatsapp()) {
			List<ScheduleResponse> listSchedule = getActiveSchedulesByDate(date);
			
			if (!listSchedule.isEmpty()) {
				error = whatsappService.sendMultipleMessages(listSchedule);
			} else error = true; //se lista vazia, msg não enviadas
		}
		
		return error;
	}	
	
}
