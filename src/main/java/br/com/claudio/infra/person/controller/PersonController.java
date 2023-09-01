package br.com.claudio.infra.person.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.entities.person.model.Person;
import br.com.claudio.infra.person.dto.RequestPersonCreate;
import br.com.claudio.infra.person.dto.RequestPersonUpdate;
import br.com.claudio.usecase.person.PersonCreateInput;
import br.com.claudio.usecase.person.PersonUpdateInput;
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
	@ResponseStatus(HttpStatus.CREATED)
	public Person createPerson(@RequestBody @Valid RequestPersonCreate request) {
		
		PersonCreateInput personCreateInput = modelMapper().map(request, PersonCreateInput.class);
		
		//System.err.println("CONTROLLER");
		//System.err.println(personCreateInput.toString());
		
		return personUseCase.createPerson(personCreateInput);
	}
	
	@PutMapping
	public Person updatePerson(@RequestBody @Valid RequestPersonUpdate request) {
		PersonUpdateInput personUpdateInput = modelMapper().map(request, PersonUpdateInput.class);
		
		return personUseCase.updatePerson(personUpdateInput);
	}
	
	@GetMapping("/{id}")
	public Person findPersonById(@PathVariable("id") Long id) {
		return personUseCase.findPersonById(id);
	}
	
	@GetMapping("/name/{partialName}")
	public List<Person> searchPerson(@PathVariable("partialName") String partialName) {
		return personUseCase.searchPersonByName(partialName);
	}	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Person> listOneOrAll(@RequestParam(required = false) String fullName, String cpf, Integer rg) {
		return personUseCase.list(fullName, cpf, rg);
	}
	
	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable("id") Long id) {
		personUseCase.deletePerson(id);
	}
	

}
