package br.com.claudio.infra.config.whatsapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.schedule.dto.ScheduleResponse;

@Service
public class WhatsappService {
	
    @Autowired
    private WebDriver webDriver;	
	
	private final SimpleMessage simpleMessage;
	

	public WhatsappService(SimpleMessage simpleMessage) {
		this.simpleMessage = simpleMessage;
	}
	
	//Chat boot
	public Boolean sendMultipleMessages(List<ScheduleResponse> listSchedule) {
		
		for (ScheduleResponse schedule: listSchedule) {
			String phone = getFormatedPhoneNumber(schedule.getPatient().getPhone());
			String firstName = schedule.getPatient().getNickName();
			String dia = getDiaDaSemana(schedule.getStartDate());
			String time = getFormatedTime(schedule.getStartDate());
			String gender = schedule.getPatient().getGender().name();
			String professionalName = schedule.getProfessional().getNickName();		
			
			String message = greetingMessage()+", "+firstName+", tudo bem?\r\n"+
					"Gostaria de confirmar seu horário marcado com "+
					getGender(gender)+" "+professionalName+
					" para "+ dia +" às *"+time+"*.\r\n"+
					"Caso não possa comparecer, avise-nos por gentileza.\r\n\r\n"+
					"Qualquer questão, não hesite em contatar-nos.\r\n\r\n"+
					"Os melhores cumprimentos ☺";
			
		      try {
		          var contactElement = findContact(phone);
                  if (!contactElement.isDisplayed()) contactElement = findContact(firstName);
		          
		          contactElement.click();
		
		          var messageBox = findTextBox();
		          messageBox.sendKeys(message);
		          messageBox.sendKeys(Keys.RETURN);
		      } catch (Exception e) {
		          System.err.println("Não foi possível enviar a mensagem para o contato {} " + phone);
	          }
		}		
		
        return true;
	}
	
    private WebElement findContact(String contact) {
        var xPathContato = "//*[@id=\"pane-side\"]/*//span[@title='" + contact + "']";
        return webDriver.findElement(By.xpath(xPathContato));
    }

    private WebElement findTextBox() {
        var xPathTextBox = "//*[@id=\"main\"]/footer//*[@role='textbox']";
        return webDriver.findElement(By.xpath(xPathTextBox));
    }	
	
	public void sendSingleMessage(Schedule schedule) {
		
		if (schedule.getId() != null) {
			String phone = schedule.getPatient().getPerson().getPhone();
			String firstName = schedule.getPatient().getNickName();
			String dateAndTime = getFormatedDateAndTime(schedule.getStartDate());
			String gender = schedule.getPatient().getPerson().getGender().name();
			String professionalName = schedule.getProfessional().getNickName();
			String message = greetingMessage()+", "+firstName+"!\r\n"+
					"Seu horário está agendado para o dia *"+dateAndTime+"*,\r\n"+
					"com "+getGender(gender)+" "+professionalName+".\r\n\r\n"+
					"Os melhores cumprimentos,\r\n\r\n"+
					"Gratos!";
			
			String url = "https://api.whatsapp.com/send?phone="
					+getFormatedPhoneNumber(phone)+"&text="
					+encodeValue(message);
			simpleMessage.sendMessage(url);
		}
	}
	
	private String getFormatedPhoneNumber(String phone) {
		var cleanPhone = (phone!=null ? phone.trim().replaceAll("[^0-9]", "") : "");
		return "55"+cleanPhone;
	}
	
	private String encodeValue(String value)  {
	    try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    return "";
	}
	
	private String greetingMessage() {
		var hora = LocalDateTime.now().getHour();
		
		return (hora <= 5) ? "Boa madrugada" :
	         (hora < 12) ? "Bom dia" :
	         (hora < 18) ? "Boa tarde" :
	         "Boa noite";		
	}
	
	private String getFormatedDateAndTime(LocalDateTime dateTime) {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH:mm");
		
		String date = formatterData.format(dateTime);
		String hours = formatterHours.format(dateTime);
		
		return date + " às " + hours;
	}
	
	private String getFormatedTime(LocalDateTime dateTime) {
		DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH:mm");

		String hours = formatterHours.format(dateTime);
		return hours;
	}
	
	private String getDiaDaSemana(LocalDateTime dateTime) {
	    LocalDate today = LocalDate.now();
	    LocalDate tomorrow = today.plusDays(1);
	    
	    LocalDate scheduleDate = dateTime.toLocalDate();
	    DayOfWeek dayOfWeek = scheduleDate.getDayOfWeek();
	    
	    String diaSemana = "amanhã";
	    
		if (!scheduleDate.equals(tomorrow)) {
			switch (dayOfWeek) {
			case SUNDAY: {
				diaSemana = "domingo";
				break;
			}
			case MONDAY: {
				diaSemana = "segunda-feira";
				break;
			}	
			case TUESDAY: {
				diaSemana = "terça-feira";
				break;
			}	
			case WEDNESDAY: {
				diaSemana = "quarta-feira";
				break;
			}
			case THURSDAY: {
				diaSemana = "quinta-feira";
				break;
			}	
			case FRIDAY: {
				diaSemana = "sexta-feira";
				break;
			}	
			case SATURDAY: {
				diaSemana = "sábado";
				break;
			}			
			default:
				break;
			}			
		}
		
		return diaSemana;
	}
	
	private String getGender(String gender) {
		return gender == "MASCULINO" ? "o" : "a";
	}

}
