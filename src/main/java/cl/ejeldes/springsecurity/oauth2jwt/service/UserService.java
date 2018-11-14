package cl.ejeldes.springsecurity.oauth2jwt.service;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;

/**
 * Created by emilio on Nov 13, 2018
 */
public interface UserService {

    UserDTO save(UserDTO user);
}
