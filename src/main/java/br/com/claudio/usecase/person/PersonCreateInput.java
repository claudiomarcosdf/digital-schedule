package br.com.claudio.usecase.person;

import java.time.LocalDate;

import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.config.db.schemas.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateInput {
	
	@NotEmpty
	private String fullName;
	
	@NotEmpty
	@Email
	private String email;
	
	private LocalDate birthDay;
	
	@NotEmpty
	private String cpf;
	
	private Integer rg;
	
	private String gender;	
	
	private String address;
	
	private Integer zipCode;
	
	private String phone;

	private String phone2;
	
	private PersonType personType;	
	
	public Gender getGender() {
		return Gender.valueOf(this.gender.toUpperCase());
	}
}
