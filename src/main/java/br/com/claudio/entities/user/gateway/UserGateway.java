package br.com.claudio.entities.user.gateway;

import java.util.Optional;

import org.springframework.security.core.Authentication;

import br.com.claudio.entities.user.model.User;

public interface UserGateway {
	
	void create(User user);
	
	User update(User user);
	
	void delete(Long id);
	
	Optional<User> findByLogin(String login);
	
	User parseUser(Authentication auth);

}
