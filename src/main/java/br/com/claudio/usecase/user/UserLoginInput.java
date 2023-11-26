package br.com.claudio.usecase.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInput {
	
    @NotNull
	private String login; //email
	
    @NotNull
    private String password;

}
