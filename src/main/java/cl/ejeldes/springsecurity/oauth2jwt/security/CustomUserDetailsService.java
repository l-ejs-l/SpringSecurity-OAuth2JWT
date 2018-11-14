package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by emilio on Nov 13, 2018
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String message = "No existe el usuario con '" + username + "' en el sistema";

        logger.debug("Loading User with username-email: '" + username + "'");

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(message));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());

        if (authorities.isEmpty()) throw new UsernameNotFoundException(message);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
