package br.com.claudio.infra.config.db.schemas;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class PersonSchema {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name = "birth_day")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate birthDay;
	
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;
	
	private Integer rg;
	
	private String address;
	
	@Column(name = "zip_code")
	private Integer zipCode;
	
	@Column(length = 14)
	private String phone;

	@Column(length = 14)
	private String phone2;
	
	@Column(columnDefinition = "boolean default 'true'")
	private Boolean active = true;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_type_id")
	private PersonTypeSchema personType;

}
