package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class PatientSchema implements Serializable  {
	private static final long serialVersionUID = -4200858246002799693L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	@OneToOne(optional = false, fetch = FetchType.EAGER) //Não funciona se colocar CascadeType.ALL
	private PersonSchema person;

}
