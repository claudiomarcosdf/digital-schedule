package br.com.claudio.infra.user.dto;

import br.com.claudio.infra.config.db.schemas.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserUpdate {
	
	@NotNull
	private Long id;
	
    @NotNull
	private String login; //email
	
    @NotNull
    private String username;
    
    private String document;

    @NotNull
    private String password;
    
    private UserRole role;	
    
    @NotNull
    private Boolean active;	

}
