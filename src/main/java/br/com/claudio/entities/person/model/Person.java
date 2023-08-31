package br.com.claudio.entities.person.model;

import java.util.Date;

import br.com.claudio.entities.persontype.model.PersonType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	
	private Long id;
	
	@NotEmpty
	private String fullName;
	
	@NotEmpty
	@Email
	private String email;
	
	private Date birthDay;
	
	@NotEmpty
	private String cpf;
	
	private Integer rg;
	
	private String address;
	
	private Integer zipCode;
	
	private String phone;

	private String phone2;
	
	private Boolean active;
	
	@NotEmpty
	private PersonType personType;	
}
