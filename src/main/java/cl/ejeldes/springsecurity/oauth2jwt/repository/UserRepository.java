package cl.ejeldes.springsecurity.oauth2jwt.repository;

import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by emilio on Nov 13, 2018
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
