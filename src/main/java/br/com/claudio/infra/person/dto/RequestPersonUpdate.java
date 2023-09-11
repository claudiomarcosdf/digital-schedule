package br.com.claudio.infra.person.dto;

import java.time.LocalDate;

import br.com.claudio.infra.config.db.schemas.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPersonUpdate {
	
	@NotNull
	private Long id;
	
	@NotEmpty
	private String fullName;
	
	@Email
	private String email;
	
	private LocalDate birthDay;
	
	private String cpf;
	
	private Integer rg;
	
	private String gender;	
	
	private String address;
	
	private Integer zipCode; 
	
	private String phone;

	private String phone2;
	
	private Boolean active;
	
	@NotNull
	private PersonTypeCreate personType;	

}
