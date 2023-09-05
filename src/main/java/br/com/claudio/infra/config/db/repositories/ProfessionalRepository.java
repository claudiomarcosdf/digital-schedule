package br.com.claudio.infra.config.db.repositories;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudio.infra.config.db.schemas.ProfessionalSchema;

public interface ProfessionalRepository extends JpaRepository<ProfessionalSchema, Long> {
	
	<S extends ProfessionalSchema> List<S> findAll(Example<S> query);
	
	@Query("SELECT professional FROM ProfessionalSchema professional WHERE " +
			  "professional.person.active = true " +
			  "AND LOWER(professional.person.fullName) LIKE %:partialName% "+
			  "ORDER BY professional.person.fullName"
			  )
	List<ProfessionalSchema> searchByName(String partialName);
	
	List<ProfessionalSchema> findByNickName(String nickName);

	List<ProfessionalSchema> findByProfessionalTypeId(Long id);	

}
