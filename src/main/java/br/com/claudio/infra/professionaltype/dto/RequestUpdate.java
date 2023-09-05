package br.com.claudio.infra.professionaltype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUpdate(
		@NotNull(message = "O ID inválido!")
		Long id,
		@Size(max = 20, message = "O tamanho máximo para o nome é de 20 caracteres")
		@NotBlank(message = "O nome do tipo de profissional é obrigatório")
		String name) {}