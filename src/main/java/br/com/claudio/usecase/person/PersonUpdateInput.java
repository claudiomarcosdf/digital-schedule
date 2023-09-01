package br.com.claudio.usecase.person;

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
public class PersonUpdateInput {
	
	@NotNull
	private Long id;
	
	@NotEmpty
	private String fullName;
	
	@NotEmpty
	@Email
	private String email;
	
	private LocalDate birthDay;
	
	@NotEmpty
	private String cpf;
	
	private Integer rg;
	
	private String address;
	
	private Integer zipCode;
	
	private String phone;

	private String phone2;
	
	private Boolean active;
	
	@NotNull
	private Long personTypeId;	
}
