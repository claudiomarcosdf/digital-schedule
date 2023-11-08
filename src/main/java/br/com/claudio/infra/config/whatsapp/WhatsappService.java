package br.com.claudio.infra.config.whatsapp;

import static br.com.claudio.infra.config.whatsapp.helper.Util.getFormatedDateAndTime;
import static br.com.claudio.infra.config.whatsapp.helper.Util.getFormatedPhoneNumber;
import static br.com.claudio.infra.config.whatsapp.helper.Util.getGender;
import static br.com.claudio.infra.config.whatsapp.helper.Util.greetingMessage;
import static br.com.claudio.infra.config.whatsapp.helper.Util.getDayOfWeek;
import static br.com.claudio.infra.config.whatsapp.helper.Util.getFormatedTime;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.claudio.DigitalScheduleApplication;
import br.com.claudio.entities.schedule.model.Schedule;
import br.com.claudio.infra.config.whatsapp.config.WhatsappProperties;
import br.com.claudio.infra.config.whatsapp.dto.QRCodeBase64Response;
import br.com.claudio.infra.config.whatsapp.dto.instance.InstanceInfoResponse;
import br.com.claudio.infra.config.whatsapp.dto.instance.InstanceResponse;
import br.com.claudio.infra.config.whatsapp.dto.message.MessageResponse;
import br.com.claudio.infra.config.whatsapp.messages.MessageService;
import br.com.claudio.infra.schedule.dto.ScheduleResponse;

/**
 *   API USADA PARA INTERAÇÃO COM O WHATSAPP
 *   É necessário o usuário manter aberto seu aplicativo Whatsapp celular ou web
 */

@Service
public class WhatsappService {
	
	private static Logger logger = LoggerFactory.getLogger(DigitalScheduleApplication.class);
	
	private String baseUrl = "";
	private String token   = "";
	private Integer key    = null;
	
	//private InstanceResponse instance = null;
	
	private InstanceInfoResponse instanceInfo = null;
	private final WhatsappProperties whatsappProperties;
	
	private final MessageService messageService;
	
	public WhatsappService(WhatsappProperties whatsappProperties, MessageService messageService) {
		this.whatsappProperties = whatsappProperties;
		this.messageService = messageService;
	}

	private void initializeVariables() {
		baseUrl = whatsappProperties.getUrlWhatsappApi();
		token = whatsappProperties.getToken();
		key = whatsappProperties.getKey();		
	}
	private InstanceResponse getInstance() {
		initializeVariables();
		RestTemplate restTemplate = new RestTemplate();
		InstanceResponse instance = null;
		
		try {
			URI uri = new URI(baseUrl+"/instance/init?key="+key+"&token="+token);
			instance = restTemplate.getForObject(uri, InstanceResponse.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
		try {
			new Thread();
			Thread.sleep(1000); // Aguardar p/ pegar o qrcode, caso contrário não funciona!
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return instance;
	}
	
	public InstanceInfoResponse getInstanceInfo() throws Exception {
		initializeVariables();
		instanceInfo = null;
		
		//System.err.println("instance not null");
		RestTemplate restTemplate = new RestTemplate();
		
    	URI uri = new URI(baseUrl+"/instance/info?key="+key+"&token="+token);
		instanceInfo = restTemplate.getForObject(uri, InstanceInfoResponse.class);
			
			//Object res = restTemplate.getForObject(uri, Object.class);
		return instanceInfo;
	}	
	
	public QRCodeBase64Response getQRCode() {
		InstanceResponse instance = getInstance();
		QRCodeBase64Response qrCode = null;
		
		if (instance != null && !instance.error()) {
			RestTemplate restTemplate = new RestTemplate();
			
			try {
				URI uri = new URI(baseUrl+"/instance/qrbase64?key="+key+"&token="+token);
				qrCode = restTemplate.getForObject(uri, QRCodeBase64Response.class);
			} catch (Exception e) {
				System.err.println(e);
			}
			
		}
		
		if (qrCode != null && qrCode.error()) {
			this.getQRCode();
		}
		
		return qrCode;
	}
	
	private void deleteInstance() {
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			URI uri = new URI(baseUrl+"/instance/delete?key="+key+"&token="+token);
			restTemplate.delete(uri);
		} catch (URISyntaxException e) {
			System.err.println(e);
		}
			
	}
	
	public void logoutPhoneInstance() {
		initializeVariables();
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			URI uri = new URI(baseUrl+"/instance/logout?key="+key+"&token="+token);
			restTemplate.delete(uri);
			//Telefone desconectado
			deleteInstance();
			
		} catch (Exception e) {
			System.err.println(e);
			//Se instância não estiver encerrada
			if (e.toString().contains("phone isn't connected")) {
				System.err.println("Deletando instância...");
				this.deleteInstance();
			}
		}
		
	}
	
	public MessageResponse sendMessage(String phone, String message) {
		MessageResponse response = null;

		if (instanceInfo != null && instanceInfo.instanceData().user().id() != null) {
			logger.info("Enviando mensagem...");
			
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("id", phone);
			map.add("message", message);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);			
			
			try {
				URI uri = new URI(baseUrl+"/message/text?key="+key+"&token="+token);
				response = restTemplate.postForObject(uri, request, MessageResponse.class);
				//System.err.println(obj.toString());
				
			} catch (Exception e) {
				logger.error("Send Error "+e.toString());
			}
		}
		
		return response;
	}
	
	public void sendSingleMessage(Schedule schedule) {
		MessageResponse messageResponse = null;
		
		if (schedule.getId() != null) {
			String phone = schedule.getPatient().getPerson().getPhone();
			String firstName = schedule.getPatient().getNickName();
			String dateAndTime = getFormatedDateAndTime(schedule.getStartDate());
			String gender = schedule.getPatient().getPerson().getGender().name();
			String professionalName = schedule.getProfessional().getNickName();
			
			String message = "";
			try {
				message = messageService.getFileContent("schedule");
			} catch (Exception e) {
			}
			
            if (message == null || message.equals("")) { //Default message
    			message = greetingMessage()+", "+firstName+"!\r\n"+
    					"Seu horário está agendado para o dia *"+dateAndTime+"*,\r\n"+
    					"com "+getGender(gender)+" "+professionalName+".\r\n\r\n"+
    					"Os melhores cumprimentos,\r\n\r\n"+
    					"Gratos!";
            } else {
            	message = message.replace("[GREETING]", greetingMessage())
            			.replace("[NAME]", firstName)
            			.replace("[DATA]", dateAndTime)
            			.replace("[PROFESSIONAL]", getGender(gender)+" "+professionalName);
            }

            messageResponse = sendMessage(getFormatedPhoneNumber(phone), message);
		}		
		
		
		if (messageResponse == null) logger.error("Erro ao enviar mensagem");
	}
	
	public Boolean sendMultipleMessages(List<ScheduleResponse> listSchedule) {
	    Boolean error = false; 
		
	    MessageResponse messageResponse = null;
		for (ScheduleResponse schedule: listSchedule) {
			String phone = schedule.getPatient().getPhone();
			String firstName = schedule.getPatient().getNickName();
			String day = getDayOfWeek(schedule.getStartDate());
			String time = getFormatedTime(schedule.getStartDate());
			String gender = schedule.getPatient().getGender().name();
			String professionalName = schedule.getProfessional().getNickName();
			
			String message = "";
			try {
				message = messageService.getFileContent("confirmation");
			} catch (Exception e) {
			}
			
            if (message == null || message.equals("")) { //Default confirmation message
    			message = greetingMessage()+", "+firstName+", tudo bem?\r\n"+
    					"Gostaria de confirmar seu horário marcado com "+
    					getGender(gender)+" "+professionalName+
    					" para "+ day +" às *"+time+"*.\r\n"+
    					"Caso não possa comparecer, avise-nos por gentileza.\r\n\r\n"+
    					"Qualquer questão, não hesite em contatar-nos.\r\n\r\n"+
    					"Os melhores cumprimentos 😃";
            } else {
            	message = message.replace("[GREETING]", greetingMessage())
            			.replace("[NAME]", firstName)
            			.replace("[PROFESSIONAL]", getGender(gender)+" "+professionalName)
            			.replace("[DAY]", day)
            			.replace("[TIME]", time);
            }			
			
            messageResponse = sendMessage(getFormatedPhoneNumber(phone), message);
            if (messageResponse == null) {
            	logger.error("Erro ao enviar mensagem ");
            	error = true;
            }
		}		
		
		return error;
	}
	

}
