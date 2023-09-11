package br.com.claudio.infra.person.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPersonCreate {
	
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
	
	@NotNull
	private PersonTypeCreate personType;		

}
