package br.com.claudio.infra.person.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.person.model.Person;
import br.com.claudio.infra.person.dto.RequestPersonCreate;
import br.com.claudio.usecase.person.PersonCreateInput;
import br.com.claudio.usecase.person.PersonUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonController {
	
	private final PersonUseCase personUseCase;
	
	public PersonController(PersonUseCase personUseCase) {
		this.personUseCase = personUseCase;
	}
	
	@PostMapping
	public Person createPerson(@RequestBody @Valid RequestPersonCreate request) {
		
		PersonCreateInput personCreateInput = modelMapper().map(request, PersonCreateInput.class);
		
		System.err.println("CONTROLLER");
		System.err.println(personCreateInput.toString());
		
		return personUseCase.createPerson(personCreateInput);
	}

}
