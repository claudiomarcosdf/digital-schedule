package br.com.claudio.usecase.professional;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.person.model.Person;
import br.com.claudio.entities.professional.gateway.ProfessionalGateway;
import br.com.claudio.entities.professional.model.Professional;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.usecase.person.PersonCreateInput;
import br.com.claudio.usecase.person.PersonUpdateInput;
import br.com.claudio.usecase.person.PersonUseCase;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase;

@Service
public class ProfessionalUseCase {
	
	private final ProfessionalGateway professionalGateway;
	
	private final PersonUseCase personUseCase;
	
	private final ProfessionalTypeUseCase professionalTypeUseCase;
	
	public ProfessionalUseCase(ProfessionalGateway professionalGateway, PersonUseCase personUseCase, ProfessionalTypeUseCase professionalTypeUseCase) {
		this.professionalGateway = professionalGateway;
		this.personUseCase = personUseCase;
		this.professionalTypeUseCase = professionalTypeUseCase;
	}
	
	public Professional createProfessional(ProfessionalCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		ProfessionalType professionalType = professionalTypeUseCase.findProfessionalTypeById(input.getProfessionalType().getId());
		if (!professionalType.getActive()) throw new InvalidOperationException("O tipo de profissional está inativo!");
		
		PersonCreateInput personInput = modelMapper().map(input.getPerson(), PersonCreateInput.class);
		Person person = personUseCase.createPerson(personInput); //person salvo no db
		
		
		Professional professional = modelMapper().map(input, Professional.class);
		professional.setPerson(person);
		professional.setProfessionalType(professionalType);
		
		return professionalGateway.create(professional);
	}

	public Professional updateProfessional(ProfessionalUpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		ProfessionalType professionalType = professionalTypeUseCase.findProfessionalTypeById(input.getProfessionalType().getId());
		if (!professionalType.getActive()) throw new InvalidOperationException("O tipo de profissional está inativo!");
		
		Professional professional = this.findProfessionalById(input.getId());

		PersonUpdateInput personInput = modelMapper().map(input.getPerson(), PersonUpdateInput.class);
		personInput.setCreatedDate(professional.getPerson().getCreatedDate());
		personUseCase.updatePerson(personInput);
	
		Professional professionalMapped = modelMapper().map(input, Professional.class);
		professionalMapped.setProfessionalSchedule(professional.getProfessionalSchedule());
		
		return professionalGateway.update(professionalMapped);
	}
	
	public Professional findProfessionalById(Long id) throws ResourceNotFoundException {
		return professionalGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado!"));
	}
	
	public List<Professional> list(String fullName, String cpf, Integer rg) {
		return professionalGateway.list(fullName, cpf, rg);
	}
	
	public List<Professional> searchProfessionalByName(String fullName) {
		return professionalGateway.searchByName(fullName.toLowerCase());
	}
	
	public List<Professional> findByProfessionalTypeId(Long id) {
		return professionalGateway.findByProfessionalTypeId(id);
	}
	
	public void deleteProfessional(Long id) throws ResourceNotFoundException {
		var professional = professionalGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado!"));
		
		PersonUpdateInput personUpdateInput = modelMapper().map(professional.getPerson(), PersonUpdateInput.class);
		personUpdateInput.setActive(false);

		personUseCase.updatePerson(personUpdateInput);
	}	

}
