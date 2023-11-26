package br.com.claudio.infra.user.gateway;

import static br.com.claudio.infra.config.mapper.MapperConfig.modelMapper;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.claudio.entities.user.gateway.UserGateway;
import br.com.claudio.entities.user.model.User;
import br.com.claudio.infra.config.db.repositories.UserRepository;
import br.com.claudio.infra.config.db.schemas.UserSchema;

@Component
public class UserDatabaseGateway implements UserGateway {
	
	private final UserRepository userRepository;
	
	public UserDatabaseGateway(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void create(User user) {
        if (userRepository.findByLoginAndActive(user.getLogin(), true) != null) throw new DataIntegrityViolationException("Este usuário já existe!");
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);  
        	
        userRepository.save(toUserSchema(user));
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<User> findByLogin(String login) {
		return  Optional.empty();
	}
	
	@Override
	public User parseUser(Authentication auth) {
		UserSchema userSchema = ((UserSchema) auth.getPrincipal());
		return toUser(userSchema);
	}
	
	private UserSchema toUserSchema(User user) {
		return modelMapper().map(user, UserSchema.class);
	}
	
	private User toUser(UserSchema userSchema) {
		return modelMapper().map(userSchema, User.class);
	}	

}
