package br.com.claudio.infra.persontype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUpdate(
		@NotNull(message = "O ID inválido!")
		Long id,
		@Size(max = 30, message = "O tamanho máximo para o nome é de 30 caracteres")
		@NotBlank(message = "O nome do tipo de pessoa é obrigatório")
		String name, Boolean active) {}
         
        