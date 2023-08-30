package br.com.claudio.infra.persontype.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.persontype.model.PersonType;
import br.com.claudio.infra.persontype.dto.RequestCreate;
import br.com.claudio.infra.persontype.dto.RequestUpdate;
import br.com.claudio.usecase.persontype.PersonTypeUseCase;
import br.com.claudio.usecase.persontype.PersonTypeUseCase.CreateInput;
import br.com.claudio.usecase.persontype.PersonTypeUseCase.UpdateInput;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persontypes")
public class PersonTypeController {
	
	private final PersonTypeUseCase personTypeUseCase;
	
	public PersonTypeController(PersonTypeUseCase personTypeUseCase) {
		this.personTypeUseCase = personTypeUseCase;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PersonType createPersonType(@RequestBody @Valid RequestCreate request) {
		
		return personTypeUseCase.createPersonType(new CreateInput(request.name()));
	}
	
	@PutMapping
	public PersonType updatePersonType(@RequestBody @Valid RequestUpdate request) {
		
		return personTypeUseCase.updatePersonType(new UpdateInput(request.id(), request.name())); 
	}
	
	@GetMapping("/{id}")
	public PersonType findPersonTypeById(@PathVariable("id") Long id) {
		
		return personTypeUseCase.findPersonTypeById(id);
	}
	
	@GetMapping
	public List<PersonType> allPersonType() {
		
		return personTypeUseCase.findAllPersonType();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePersonType(@PathVariable Long id) {
		personTypeUseCase.deletePersonType(id);
	}

}
