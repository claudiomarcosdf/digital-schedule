package br.com.claudio.common;

import br.com.claudio.infra.config.db.schemas.PersonSchema;
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
public class PatientH2 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	@OneToOne(optional = false, fetch = FetchType.EAGER) //Não funciona se colocar CascadeType.ALL
	private PersonSchema person;	

}
