package cl.ejeldes.springsecurity.oauth2jwt.dto.mapper;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.Role;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.exception.ResourceNotFoundException;
import cl.ejeldes.springsecurity.oauth2jwt.util.dto.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by emilio on Nov 13, 2018
 */
@Component
public class UserMapper extends AbstractMapper<UserDTO, User> {

    @Override
    public UserDTO convertToDTO(User user) {
        if (user == null) {
            logger.debug("UserMapper.convertToDTO(): user is null");
            throw new ResourceNotFoundException("UserMapper.convertToDTO()");
        }

        UserDTO dto = new UserDTO();
        if (user.getId() != null && user.getId() != 0) dto.setId(user.getId());
        if (user.getEmail() != null && !user.getEmail().equals("")) dto.setUsername(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().equals("")) dto.setPassword(user.getPassword());
        if (user.getName() != null && !user.getName().equals("")) dto.setName(user.getName());
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            Set<String> roles = new HashSet<>();

            for (Role role : user.getRoles()) {
                roles.add(role.getAuthority());
            }

            dto.setRoles(roles);
        }

        return dto;
    }

    @Override
    public User convertToEntity(UserDTO dto) {
        if (dto == null) {
            logger.debug("UserMapper.convertToEntity(): userDTO is null");
            throw new ResourceNotFoundException("UserMapper.convertToEntity()");
        }

        User user = new User();
        if (dto.getRoles() != null && dto.getRoles().size() > 0) {
            Set<Role> roles = new HashSet<>();

            for (String role : dto.getRoles()) {
                Role r = new Role();
                r.setAuthority(role);
                roles.add(r);
            }

            user.setRoles(roles);
        }

        if (dto.getUsername() != null && !dto.getUsername().equals("")) user.setEmail(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().equals("")) user.setPassword(dto.getPassword());
        if (dto.getName() != null && !dto.getName().equals("")) user.setName(dto.getName());

        return user;
    }
}
