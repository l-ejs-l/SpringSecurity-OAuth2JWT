package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.Role;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.exception.ResourceNotFoundException;
import cl.ejeldes.springsecurity.oauth2jwt.repository.RoleRepository;
import cl.ejeldes.springsecurity.oauth2jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by emilio on Nov 13, 2018
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    public CustomOAuth2UserService(BCryptPasswordEncoder passwordEncoder,
                                   CustomUserDetailsService customUserDetailsService,
                                   UserRepository userRepository,
                                   RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return buildPrincipal(oAuth2User);
    }

    public CustomPrincipal buildPrincipal(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        logger.info("Attributes: \n", attributes);

        User user = userDetailsService.findUserByUsername(email).orElseGet(() -> {
            User u = new User();
            Set<Role> roles = new HashSet<>();

            Role role_user = roleRepository.findByAuthority("ROLE_USER").orElseThrow(ResourceNotFoundException::new);
            roles.add(role_user);

            u.setRoles(roles);
            u.setName(name);
            u.setEmail(email);
            u.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            u.setEnable(true);
            return u;
        });

        logger.debug("USER: \n" + user);

        User save = userRepository.save(user);
        logger.debug("SAVED" + save.toString());

        UserDTO userDTO = user.toUserDTO();

        logger.debug("USER DTO: \n" + userDTO);

        CustomPrincipal principal = new CustomPrincipal(userDTO);
        principal.setAttributes(attributes);
        principal.setName(oAuth2User.getName());

        logger.debug("PRINCIPAL: \n" + principal.toString());
        return principal;
    }
}
