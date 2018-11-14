package cl.ejeldes.springsecurity.oauth2jwt.service;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.dto.mapper.UserMapper;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.Role;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.exception.ResourceNotFoundException;
import cl.ejeldes.springsecurity.oauth2jwt.repository.RoleRepository;
import cl.ejeldes.springsecurity.oauth2jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by emilio on Nov 13, 2018
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO save(UserDTO user) {
        Set<Role> roles = new HashSet<>();
        for (String role : user.getRoles()) {
            Role r = roleRepository.findByAuthority(role).orElseThrow(ResourceNotFoundException::new);
            roles.add(r);
        }

        User u = userMapper.convertToEntity(user);
        u.setRoles(roles);

        return userMapper.convertToDTO(userRepository.save(u));
    }
}
