package br.com.claudio.infra.config.whatsapp.dto.instance;

public record InstanceResponse(Boolean error, String message, String key, QRCode qrcode, Browser browser) {

}

