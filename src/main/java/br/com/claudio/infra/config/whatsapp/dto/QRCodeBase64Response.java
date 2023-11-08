package br.com.claudio.infra.config.whatsapp.dto;

public record QRCodeBase64Response(Boolean error, String message, String qrcode) {

}
