package br.com.claudio.infra.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserLogin {
	
    @NotNull
	private String login; //email
	
    @NotNull
    private String password;	
}
