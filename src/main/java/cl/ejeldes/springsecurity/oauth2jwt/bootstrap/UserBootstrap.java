package cl.ejeldes.springsecurity.oauth2jwt.bootstrap;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.Role;
import cl.ejeldes.springsecurity.oauth2jwt.repository.RoleRepository;
import cl.ejeldes.springsecurity.oauth2jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by emilio on Nov 13, 2018
 */
@Component
public class UserBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public UserBootstrap(RoleRepository roleRepository,
                         BCryptPasswordEncoder encoder,
                         UserService userService) {
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        insertRoles();
        insertUsers();
    }

    private void insertUsers() {
        Set<String> roles1 = new HashSet<>();
        Set<String> roles2 = new HashSet<>();
        roles1.add("ROLE_USER");
        roles1.add("ROLE_ADMIN");
        roles2.add("ROLE_USER");

        UserDTO dto1 = new UserDTO();
        dto1.setRoles(roles1);
        dto1.setUsername("emilio.jeldes@gmail.com");
        dto1.setPassword(encoder.encode("12345"));

        UserDTO dto2 = new UserDTO();
        dto2.setRoles(roles2);
        dto2.setUsername("mail@gmail.com");
        dto2.setPassword(encoder.encode("12345"));


        userService.save(dto1);
        userService.save(dto2);
    }

    private void insertRoles() {
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setAuthority("ROLE_ADMIN");
        role2.setAuthority("ROLE_USER");

        roleRepository.save(role1);
        roleRepository.save(role2);
    }
}
