package br.com.claudio.infra.professionaltype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCreate(
		@NotBlank(message = "O nome do tipo de profissional é obrigatório")
		@Size(max = 20, message = "O tamanho máximo para o nome é de 20 caracteres")
		String name) {}