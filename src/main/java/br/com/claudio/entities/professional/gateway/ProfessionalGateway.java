package br.com.claudio.entities.professional.gateway;

import java.util.List;
import java.util.Optional;

import br.com.claudio.entities.professional.model.Professional;

public interface ProfessionalGateway {
	
	Professional create(Professional professional);
	
	Professional update(Professional professional);
	
	void delete(Long id);
	
	List<Professional> findByProfessionalTypeId(Long id);
	
	List<Professional> searchByNickName(String nickName);	
	
	Optional<Professional> findById(Long id);
	
	List<Professional> list(String fullName, String cpf, Integer rg);
	
	List<Professional> searchByName(String partialName);
}
