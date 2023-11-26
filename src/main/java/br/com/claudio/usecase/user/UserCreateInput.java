package br.com.claudio.usecase.user;

import br.com.claudio.infra.config.db.schemas.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateInput {
	
    @NotNull
	private String login; //email
	
    @NotNull
    private String username;
    
    private String document;

    @NotNull
    private String password;
    
    private UserRole role;
}
