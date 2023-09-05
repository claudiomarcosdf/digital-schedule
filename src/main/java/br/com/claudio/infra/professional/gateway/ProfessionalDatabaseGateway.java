package br.com.claudio.infra.professional.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import br.com.claudio.entities.professional.gateway.ProfessionalGateway;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.infra.config.db.repositories.ProfessionalRepository;
import br.com.claudio.infra.config.db.schemas.PersonSchema;
import br.com.claudio.infra.config.db.schemas.ProfessionalSchema;

@Component
public class ProfessionalDatabaseGateway implements ProfessionalGateway {
	
	private ProfessionalRepository professionalRepository;
	
	public ProfessionalDatabaseGateway(ProfessionalRepository professionalRepository) {
		this.professionalRepository = professionalRepository;
	}

	@Override
	public Professional create(Professional professional) {
        ProfessionalSchema professionalSchema = toProfessionalSchema(professional);
		return toProfessional(professionalRepository.save(professionalSchema));
	}

	@Override
	public Professional update(Professional professional) {
		ProfessionalSchema professionalSchema = toProfessionalSchema(professional);
		return toProfessional(professionalRepository.save(professionalSchema));
	}
	
	@Override
	public Optional<Professional> findById(Long id) {
		return professionalRepository.findById(id)
				.map(schema -> toProfessional(schema));
	}	

	@Override
	public void delete(Long id) {
		// Apenas exclusão lógica
	}

	@Override
	public List<Professional> findByProfessionalTypeId(Long id) {
		return toProfessionalList(professionalRepository.findByProfessionalTypeId(id));
	}

	@Override
	public List<Professional> searchByNickName(String nickName) {
		return toProfessionalList(professionalRepository.findByNickName(nickName));
	}


	@Override
	public List<Professional> list(String fullName, String cpf, Integer rg) {
		PersonSchema personSchema = new PersonSchema();
		personSchema.setFullName(fullName);
		personSchema.setCpf(cpf);
		personSchema.setRg(rg);
		
		ProfessionalSchema professionalSchema = new ProfessionalSchema();
		professionalSchema.setPerson(personSchema);
		
		Example<ProfessionalSchema> query = QueryBuilderProfessional.makeQuery(professionalSchema);
		
		return toProfessionalList(professionalRepository.findAll(query));
	}

	@Override
	public List<Professional> searchByName(String partialName) {
		return toProfessionalList(professionalRepository.searchByName(partialName));
	}
	
	private Professional toProfessional(ProfessionalSchema professionalSchema) {
		return modelMapper().map(professionalSchema, Professional.class);
	}

	private ProfessionalSchema toProfessionalSchema(Professional professional) {
		return modelMapper().map(professional, ProfessionalSchema.class);
	}	
	
	private List<Professional> toProfessionalList(List<ProfessionalSchema> listProfessionalSchema) {
		
		List<Professional> professionalList = new ArrayList<>();
		for (ProfessionalSchema professionalSchema: listProfessionalSchema) {
			professionalList.add(toProfessional(professionalSchema));
		}

		return professionalList;
	}		

}
