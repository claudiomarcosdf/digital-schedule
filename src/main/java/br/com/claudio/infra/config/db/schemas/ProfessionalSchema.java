package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professionals")
public class ProfessionalSchema implements Serializable {
	private static final long serialVersionUID = 3421570044462810272L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@NotNull
	@Column(length = 20, unique = true)
	private Integer document;
	
	@Column(name = "duration_service")
	private Integer durationService;
	
	@Column(name = "interval_service")
	private Integer intervalService = 0;
	
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	private PersonSchema person;
	
	@JoinColumn(name = "professional_type_id", referencedColumnName = "id")
	@ManyToOne
	private ProfessionalTypeSchema professionalType;

}
