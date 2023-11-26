package br.com.claudio.infra.user.gateway;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.claudio.infra.config.db.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public AuthorizationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByLoginAndActive(username, true);
		
		System.err.println("user "+user);
		
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Not found: " + username);
        }
	}

}
