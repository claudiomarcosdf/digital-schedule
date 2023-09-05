package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "procedures")
public class ProcedureSchema implements Serializable  {
	private static final long serialVersionUID = -6549618065143379187L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@Column(length = 40, nullable = false)
	private String name;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	@Column(columnDefinition = "boolean default 'true'")
	private Boolean active = true;	
	
	@JoinColumn(name = "professional_type_id", referencedColumnName = "id")
	@ManyToOne
	private ProfessionalTypeSchema professionalType;

}
