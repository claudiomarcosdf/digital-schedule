package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.claudio.infra.config.db.schemas.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persons")
@EntityListeners(AuditingEntityListener.class)
public class PersonSchema implements Serializable {
	private static final long serialVersionUID = 4345295048630389812L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@Email
	@Column(nullable = true)
	private String email;
	
	@Column(name = "birth_day")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate birthDay;
	
	@Column(length = 11)
	private String cpf;
	
	private Integer rg;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Gender gender;
	
	private String address;
	
	@Column(name = "zip_code")
	private Integer zipCode;
	
	@Column(length = 14)
	private String phone;

	@Column(length = 14)
	private String phone2;
	
	@Column(columnDefinition = "boolean default 'true'")
	private Boolean active = true;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_type_id")
	private PersonTypeSchema personType;
	
	@OneToOne(mappedBy = "person", fetch = FetchType.EAGER)
	@JsonIgnore
	private PatientSchema patient;
	
	@OneToOne(mappedBy = "person", fetch = FetchType.EAGER)
	@JsonIgnore
	private ProfessionalSchema professional;
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedDate;
	
	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		this.phone = (this.phone!=null?this.phone.trim().replaceAll("[^0-9]", ""):null);
		this.phone2 = (this.phone2!=null?this.phone2.trim().replaceAll("[^0-9]", ""):null);
		this.cpf = (this.cpf!=null?this.cpf.trim().replaceAll("\\.|-|/", ""):null);
		
	}

}
