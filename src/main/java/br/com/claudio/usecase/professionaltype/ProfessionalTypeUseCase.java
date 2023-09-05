package br.com.claudio.usecase.professionaltype;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.exception.ResourceNotFoundException;
import br.com.claudio.entities.professionaltype.gateway.ProfessionalTypeGateway;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import jakarta.validation.constraints.NotBlank;

@Service
public class ProfessionalTypeUseCase {
	
	private final ProfessionalTypeGateway professionalTypeGateway;
	
	public ProfessionalTypeUseCase(ProfessionalTypeGateway professionalTypeGateway) {
		this.professionalTypeGateway = professionalTypeGateway;
	}

	public ProfessionalType createProfessionalType(CreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		var professionalType = new ProfessionalType(input.name());
		
		return professionalTypeGateway.create(professionalType);
	}

	public ProfessionalType updateProfessionalType(UpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		this.findProfessionalTypeById(input.id());
		
		var professionalType = new ProfessionalType(input.id(), input.name(), input.active());
		
		return professionalTypeGateway.update(professionalType);
	}

	public ProfessionalType findProfessionalTypeById(Long id) {
		return professionalTypeGateway
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de profissional não encontrado!"));	
	}
	
	public ProfessionalType findProfessionalTypeByName(String name) throws ResourceNotFoundException {
		return professionalTypeGateway
				.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de profissional não encontrado!"));	
	}
	public List<ProfessionalType> findAllProfessionalType() {
		return professionalTypeGateway.findAll();
	}

	public void deleteProfessionalType(Long id) {
		ProfessionalType professionalType = this.findProfessionalTypeById(id);
		professionalType.setActive(false);
		
		professionalTypeGateway.update(professionalType); //Remoção lógica
	}
	
	public record CreateInput(String name) {};
	public record UpdateInput(Long id, @NotBlank String name, Boolean active) {};	

}
