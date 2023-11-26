package br.com.claudio.infra.config.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.claudio.infra.config.db.schemas.UserSchema;

public interface UserRepository extends JpaRepository<UserSchema, Long>{
	
	UserDetails findByLoginAndActive(String login, Boolean active);
}
