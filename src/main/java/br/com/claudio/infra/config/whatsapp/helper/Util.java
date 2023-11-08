package br.com.claudio.infra.config.whatsapp.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	
	public static String getFormatedPhoneNumber(String phone) {
		if (phone == null || phone.equals("")) return null;
		
		var cleanPhone = (phone!=null ? phone.trim().replaceAll("[^0-9]", "") : "");
		var codeArea = cleanPhone.substring(0, 2);
		var phoneNumber = cleanPhone.substring(2, cleanPhone.length()); //phone number without code area
		
		if (Integer.parseInt(codeArea) > 30) {
			if (phoneNumber.length() == 9)
  			   phoneNumber = phoneNumber.substring(1, phoneNumber.length()); //remove o primeiro nove caso o cod área for maior que 30
		} else {
			if (phoneNumber.length() == 8)
			   phoneNumber = "9"+phoneNumber; //inseri um 9 na frente
		}
		
		return "55"+codeArea+phoneNumber;
	}
	
	public static String encodeValue(String value)  {
	    try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    return "";
	}
	
	public static String greetingMessage() {
		var hora = LocalDateTime.now().getHour();
		
		return (hora <= 5) ? "Boa madrugada" :
	         (hora < 12) ? "Bom dia" :
	         (hora < 18) ? "Boa tarde" :
	         "Boa noite";		
	}
	
	public static String getFormatedDateAndTime(LocalDateTime dateTime) {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH:mm");
		
		String date = formatterData.format(dateTime);
		String hours = formatterHours.format(dateTime);
		
		return date + " às " + hours;
	}
	
	public static String getFormatedTime(LocalDateTime dateTime) {
		DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("HH:mm");

		String hours = formatterHours.format(dateTime);
		return hours;
	}
	
	public static String getDayOfWeek(LocalDateTime dateTime) {
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
	
	public static String getGender(String gender) {
		return gender == "MASCULINO" ? "o" : "a";
	}	

}
