package br.com.claudio.usecase.procedure;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.InvalidOperationException;
import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.procedure.gateway.ProcedureGateway;
import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase;

@Service
public class ProcedureUseCase {
	
	private final ProcedureGateway procedureGateway;
	
	private final ProfessionalTypeUseCase professionalTypeUseCase;
	
	public ProcedureUseCase(ProcedureGateway procedureGateway, ProfessionalTypeUseCase professionalTypeUseCase) {
		this.procedureGateway = procedureGateway;
		this.professionalTypeUseCase = professionalTypeUseCase;
	}
	
	public Procedure createProcedure(ProcedureCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		ProfessionalType professionalType = professionalTypeUseCase.findProfessionalTypeById(input.getProfessionalType().getId());
		if (!professionalType.getActive()) throw new InvalidOperationException("O tipo de profissional está inativo!");
		if (!existProcedureWithNameAndProfessionalType(input.getName(), professionalType.getId())) 
			 throw new InvalidOperationException("Procedimento já cadastrado!");
		
		Procedure procedure = modelMapper().map(input, Procedure.class);
		procedure.setProfessionalType(professionalType);
		
		return procedureGateway.create(procedure);
	}

	private boolean existProcedureWithNameAndProfessionalType(String name, Long id) {
		return procedureGateway.verifyIfExists(name.toLowerCase(), id);
	}

	public Procedure updateProcedure(ProcedureUpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		ProfessionalType professionalType = professionalTypeUseCase.findProfessionalTypeById(input.getProfessionalType().getId());
		if (!professionalType.getActive()) throw new InvalidOperationException("O tipo de profissional está inativo!");
		
		Procedure procedure = modelMapper().map(input,  Procedure.class);
		procedure.setProfessionalType(professionalType);
		return procedureGateway.update(procedure);
	}
	
	public Procedure findProcedureById(Long id) throws ResourceNotFoundException {
		return procedureGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Procedimento não encontrado!"));
	}

	public Procedure deleteProcedureById(Long id) throws ResourceNotFoundException {
		Procedure procedure = procedureGateway.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Procedimento não encontrado!"));
		
		procedure.setActive(false);
		return procedureGateway.update(procedure);
	}

	public List<Procedure> searchProcedureByName(String partialName) {
		return procedureGateway.searchByName(partialName);
	}

	public List<Procedure> findProfessionalTypeId(Long id) {
		return procedureGateway.findByProfessionalTypeId(id);
	}


}
