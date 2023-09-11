package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professional_schedules")
public class ProfessionalScheduleSchema implements Serializable {
	private static final long serialVersionUID = 6842818253823623803L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 30)
	private String monday;
	
	@Column(length = 30)
	private String tuesday;
	
	@Column(length = 30)
	private String wednesday;
	
	@Column(length = 30)
	private String thursday;
	
	@Column(length = 30)
	private String friday;
	
	@Column(length = 30)
	private String saturday;
	
	@Column(length = 30)
	private String sunday;

}
