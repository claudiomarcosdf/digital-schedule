package br.com.claudio.infra.config.whatsapp.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.infra.config.whatsapp.WhatsappService;
import br.com.claudio.infra.config.whatsapp.dto.FileMessage;
import br.com.claudio.infra.config.whatsapp.dto.QRCodeBase64Response;
import br.com.claudio.infra.config.whatsapp.dto.instance.InstanceInfoResponse;
import br.com.claudio.infra.config.whatsapp.dto.message.MessageResponse;
import br.com.claudio.infra.config.whatsapp.messages.MessageService;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {
	
	private final WhatsappService whatsappService;
	
	private final MessageService messageService;

	public WhatsappController(WhatsappService whatsappService, MessageService messageService) { 
		this.whatsappService = whatsappService;
		this.messageService = messageService;
	}
	
	@GetMapping("/instanceinfo")
	public ResponseEntity<?> getInstanceInfo() {
		
		try {
			InstanceInfoResponse instanceInfo = whatsappService.getInstanceInfo();
			return ResponseEntity.ok().body(instanceInfo);
			
		} catch (Exception e) {
			return new ResponseEntity<>("Instância não iniciada", HttpStatus.NOT_FOUND);
		}
		
		// Optional<InstanceInfoResponse> responseOpt = Optional.of(whatsappService.getInstanceInfo());
	}		
	
	@GetMapping(value = "/qrcode")
	public ResponseEntity<?> getQRCode() {
		
		QRCodeBase64Response qrCode = whatsappService.getQRCode();
		
		return qrCode != null 
				? ResponseEntity.ok().body(qrCode) 
				: new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/qrcodehtml", produces = MediaType.TEXT_HTML_VALUE)
	public String getQRCodeHtml() {
		
		QRCodeBase64Response qrCode = whatsappService.getQRCode();
		
		return qrCode != null ? 
				"<html>\n" + "<header><title>QR CODE</title></header>\n" +
		          "<body>\n" + "<img src='"+qrCode.qrcode()+"'/>\n" + "</body>\n" + "</html>"				
				: "";
	}	
	
	@PostMapping("/send")
	public String sendMessageTest() {
		
		var messageDefault = "Mensagem teste advinda da API Digital Schedule.\\n Com teste de caracter *encoded*.";
		
		try {
			messageDefault = messageService.getFileContent("agendamento");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Se o DDD do telefone for menor ou igual a 30 acrescentar um ZERO na frente do número 
		MessageResponse message = whatsappService.sendMessage("556199763771", messageDefault);
		
		if (message == null) return "Mensagem não enviada!";
		else return "Mensagem enviada com sucesso!";
	}
	
	@DeleteMapping("/logout")
	public String encerrar() {
		
		whatsappService.logoutPhoneInstance();
		
		return "Conexão encerrada";
	}		
	
//  ######################### Default Messages Routes ###########################################
	
	@PostMapping("/filemessage")
	public ResponseEntity<?> saveFileMessage(@RequestBody FileMessage fileMessage) {
		//Save and update files (schedule.txt for scheduling messages |  confirmationschedule.txt for schedule confirmation messages)
		
		try {
			messageService.saveFileContent(fileMessage.fileName(), fileMessage.message());
			return ResponseEntity.ok().body("");
		} catch (IOException e) {
			return new ResponseEntity<>("Erro ao salvar arquivo", HttpStatus.BAD_REQUEST);
		}		
	}
	
	@GetMapping(value = "/filemessage/{filename}")
	public ResponseEntity<?> getFileMessage(@PathVariable("filename") String fileName) {
		
		try {
			String message = messageService.getFileContent(fileName);
			return ResponseEntity.ok().body(message);
		} catch (IOException e) {
			return new ResponseEntity<>("Erro ao ler arquivo", HttpStatus.BAD_REQUEST);
		}		
	}	
	

}
