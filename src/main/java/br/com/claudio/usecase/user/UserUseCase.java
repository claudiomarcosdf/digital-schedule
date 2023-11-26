package br.com.claudio.usecase.user;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.claudio.entities.exception.RequiredObjectIsNullException;
import br.com.claudio.entities.user.gateway.UserGateway;
import br.com.claudio.entities.user.model.User;
import br.com.claudio.infra.config.security.TokenService;
import br.com.claudio.infra.user.dto.UserResponse;

@Service
public class UserUseCase {
	
	private final UserGateway userGateway;
	
	private AuthenticationManager authenticationManager;
	
	private TokenService tokenService;

	public UserUseCase(UserGateway userGateway, AuthenticationManager authenticationManager,
			TokenService tokenService) {
		this.userGateway = userGateway;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	public void createUser(UserCreateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		User user = modelMapper().map(input, User.class);
		
		userGateway.create(user);
	}
	
	public User updateUser(UserUpdateInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		User user = modelMapper().map(input, User.class);
		
		return userGateway.update(user);
	}
	
	public UserResponse login(UserLoginInput input) {
		if (input == null) throw new RequiredObjectIsNullException();
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(input.getLogin(), input.getPassword());
		var auth = authenticationManager.authenticate(usernamePassword);
		
		User user = userGateway.parseUser(auth);
		var token = tokenService.generateToken(user);
		
		UserResponse userResponse = modelMapper().map(user, UserResponse.class);
		userResponse.setToken(token);
		
		return userResponse;
	}	

}
