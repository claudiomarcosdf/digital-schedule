package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professional_types")
public class ProfessionalTypeSchema implements Serializable  {
	private static final long serialVersionUID = 9082289567962782859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long id;
	
	@Column(length = 20, nullable = false, unique = true)
	private String name;
	
	@Column(columnDefinition = "boolean default 'true'")
	private Boolean active = true;
	
	@OneToMany(mappedBy = "professionalType", fetch = FetchType.LAZY)
	private List<ProcedureSchema> procedures;
	
	@OneToMany(mappedBy = "professionalType", fetch = FetchType.LAZY)
	private List<ProfessionalSchema> professionals;	

	public ProfessionalTypeSchema(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ProfessionalTypeSchema(Long id, String name, Boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}
}
