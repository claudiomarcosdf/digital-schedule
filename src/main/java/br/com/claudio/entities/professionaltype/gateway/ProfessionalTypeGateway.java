package br.com.claudio.entities.professionaltype.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;

public interface ProfessionalTypeGateway {
	
	ProfessionalType create(ProfessionalType professionalType);
	
	ProfessionalType update(ProfessionalType professionalType);
	
	void delete(Long id);
	
	Optional<ProfessionalType> findById(Long id);
	
	Optional<ProfessionalType> findByName(String name);
	
	List<ProfessionalType> findAll();

}
