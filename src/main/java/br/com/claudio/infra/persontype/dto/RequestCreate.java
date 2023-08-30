package br.com.claudio.infra.persontype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCreate(
		@NotBlank(message = "O nome do tipo de pessoa é obrigatório")
		@Size(max = 30, message = "O tamanho máximo para o nome é de 30 caracteres")
		String name) {}