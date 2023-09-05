package br.com.claudio.infra.config.db.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudio.infra.config.db.schemas.ProcedureSchema;

public interface ProcedureRepository extends JpaRepository<ProcedureSchema, Long> {
	
	@Query("SELECT procedure FROM ProcedureSchema procedure WHERE " +
			  "procedure.active = true " +
			  "AND LOWER(procedure.name) LIKE %:partialName% "+
			  "ORDER BY procedure.name"
		   )
	List<ProcedureSchema> searchByName(String partialName);

	List<ProcedureSchema> findByProfessionalTypeId(Long id);

	@Query("SELECT procedure FROM ProcedureSchema procedure WHERE " +
			  "LOWER(procedure.name) = :name "+
			  "AND procedure.professionalType.id = :id"
	      )
	Optional<ProcedureSchema> verifyIfExists(String name, Long id);	

}
