package br.com.claudio.infra.user.controller;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudio.infra.user.dto.RequestUserCreate;
import br.com.claudio.infra.user.dto.RequestUserLogin;
import br.com.claudio.infra.user.dto.UserResponse;
import br.com.claudio.usecase.user.UserCreateInput;
import br.com.claudio.usecase.user.UserLoginInput;
import br.com.claudio.usecase.user.UserUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserUseCase userUseCase;

	public UserController(UserUseCase userUseCase) {
		this.userUseCase = userUseCase;
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody @Valid RequestUserCreate request) {
		UserCreateInput userCreateInput = modelMapper().map(request, UserCreateInput.class);
		userUseCase.createUser(userCreateInput);
	}	
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public UserResponse login(@RequestBody @Valid RequestUserLogin request) {
		UserLoginInput userLoginInput = modelMapper().map(request, UserLoginInput.class);
		return userUseCase.login(userLoginInput);
	}	

}
