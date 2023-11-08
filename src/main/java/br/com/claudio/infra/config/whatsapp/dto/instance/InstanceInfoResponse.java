package br.com.claudio.infra.config.whatsapp.dto.instance;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InstanceInfoResponse(Boolean error, String message,  @JsonProperty("instance_data") InstanceData instanceData) {

}
