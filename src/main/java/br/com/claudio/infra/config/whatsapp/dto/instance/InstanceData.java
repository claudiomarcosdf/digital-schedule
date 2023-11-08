package br.com.claudio.infra.config.whatsapp.dto.instance;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InstanceData(@JsonProperty("instance_key") String instanceKey, String webhookUrl, @JsonProperty("user") UserZap user) {

}
