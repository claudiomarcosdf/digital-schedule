package br.com.claudio.infra.procedure.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.claudio.entities.procedure.gateway.ProcedureGateway;
import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.infra.config.db.repositories.ProcedureRepository;
import br.com.claudio.infra.config.db.schemas.ProcedureSchema;

@Component
public class ProcedureDatabaseGateway implements ProcedureGateway {
	
	private final ProcedureRepository procedureRepository;
	
	public ProcedureDatabaseGateway(ProcedureRepository procedureRepository) {
		this.procedureRepository = procedureRepository;
	}

	@Override
	public Procedure create(Procedure procedure) {
		ProcedureSchema procedureSchema = toProcedureSchema(procedure);
		return toProcedure(procedureRepository.save(procedureSchema));
	}
	
	@Override
	public Procedure update(Procedure procedure) {
		ProcedureSchema procedureSchema = toProcedureSchema(procedure);
		return toProcedure(procedureRepository.save(procedureSchema));
	}

	@Override
	public void delete(Long id) {
		// Não há exclusão física de procedimento
	}

	@Override
	public Optional<Procedure> findById(Long id) {
		return procedureRepository.findById(id)
				.map(schema -> toProcedure(schema));
	}

	@Override
	public List<Procedure> searchByName(String partialName) {
		List<ProcedureSchema> procedureListSchema = procedureRepository.searchByName(partialName);
		
		return toProcedureList(procedureListSchema);
	}

	@Override
	public List<Procedure> findByProfessionalTypeId(Long id) {
		List<ProcedureSchema> procedureListSchema = procedureRepository.findByActiveAndProfessionalTypeId(true, id);
		return toProcedureList(procedureListSchema);
	}	
	private ProcedureSchema toProcedureSchema(Procedure procedure) {
		return modelMapper().map(procedure, ProcedureSchema.class);
	}
	
	private Procedure toProcedure(ProcedureSchema procedureSchema) {
		return modelMapper().map(procedureSchema, Procedure.class);
	}	
	
	private List<Procedure> toProcedureList(List<ProcedureSchema> listProcedureSchema) {
		List<Procedure> procedureList = new ArrayList<>();
		
		for(ProcedureSchema procedureSchema: listProcedureSchema) {
			procedureList.add(toProcedure(procedureSchema));
		}
		
		return procedureList;
	}

	@Override
	public boolean verifyIfExists(String name, Long id) {
		return procedureRepository.verifyIfExists(name, id).isEmpty();
	}

}
