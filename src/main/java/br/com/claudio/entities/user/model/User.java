package br.com.claudio.entities.user.model;

import java.time.LocalDateTime;

import br.com.claudio.infra.config.db.schemas.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
    private Long id;
    
    @NotNull
	private String login; //email
	
    @NotNull
    private String username;
    
    private String document;

    @NotNull
    private String password;
    
    private Boolean active = false;
    
    private UserRole role;
    
    private LocalDateTime createdDate;
}
