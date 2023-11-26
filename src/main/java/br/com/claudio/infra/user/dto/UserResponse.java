package br.com.claudio.infra.user.dto;

import br.com.claudio.infra.config.db.schemas.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	
	private Long id;
	
	private String login; //email
	
    private String username;
    
    private String document;

    private UserRole role;
    
    private String token;

}
