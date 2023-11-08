package br.com.claudio.infra.config.whatsapp.dto.message;

public record MessageResponse(Boolean error, Data data, Message message, String messageTimestamp, String status) {

}
