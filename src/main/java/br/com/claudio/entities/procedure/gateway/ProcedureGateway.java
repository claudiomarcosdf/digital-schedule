package br.com.claudio.entities.procedure.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.procedure.model.Procedure;

public interface ProcedureGateway {
	
	Procedure create(Procedure procedure);
	
	Procedure update(Procedure procedure);
	
	void delete(Long id);
	
	Optional<Procedure> findById(Long id);
	
	List<Procedure> findByProfessionalTypeId(Long id);
	
	List<Procedure> searchByName(String partialName);

	boolean verifyIfExists(String name, Long id);	
	
}
