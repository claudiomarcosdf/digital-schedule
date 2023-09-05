package br.com.claudio.infra.professionaltype.gateway;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.professionaltype.gateway.ProfessionalTypeGateway;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.infra.config.db.repositories.ProfessionalTypeRepository;
import br.com.claudio.infra.config.db.schemas.ProfessionalTypeSchema;

@Component
public class ProfessionalTypeDatabaseGateway implements ProfessionalTypeGateway {
	
	private final ProfessionalTypeRepository professionalTypeRepository;
	
	public ProfessionalTypeDatabaseGateway(ProfessionalTypeRepository professionalTypeRepository) {
		this.professionalTypeRepository = professionalTypeRepository;
	}

	@Override
	public ProfessionalType create(ProfessionalType professionalType) {
		ProfessionalTypeSchema professionalTypeSchema = professionalTypeRepository.save(new ProfessionalTypeSchema(null, professionalType.getName()));
		return new ProfessionalType(professionalTypeSchema.getId(), professionalTypeSchema.getName(), professionalTypeSchema.getActive());
	}

	@Override
	public ProfessionalType update(ProfessionalType professionalType) {
		ProfessionalTypeSchema professionalTypeSchema = professionalTypeRepository.save(new ProfessionalTypeSchema(professionalType.getId(), professionalType.getName(), professionalType.getActive()));
		return new ProfessionalType(professionalTypeSchema.getId(), professionalTypeSchema.getName(), professionalTypeSchema.getActive());
	}

	@Override
	public void delete(Long id) {
       //desativado		
	}

	@Override
	public Optional<ProfessionalType> findById(Long id) {
		return professionalTypeRepository.findById(id)
				.map(schema -> new ProfessionalType(schema.getId(), schema.getName(), schema.getActive()));
	}

	@Override
	public Optional<ProfessionalType> findByName(String name) {
		return professionalTypeRepository.findByName(name)
				.map(schema -> new ProfessionalType(schema.getId(), schema.getName(), schema.getActive()));
	}

	@Override
	public List<ProfessionalType> findAll() {
		return professionalTypeRepository.findAll()
				.stream()
				.map(schema -> new ProfessionalType(schema.getId(), schema.getName(), schema.getActive()))
				.collect(toList());
	}

}
