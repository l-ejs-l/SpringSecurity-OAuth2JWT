package cl.ejeldes.springsecurity.oauth2jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by emilio on Nov 13, 2018
 */
@Getter
@Setter
@ToString
public class UserDTO {

    private String id;
    private String username;

    @JsonIgnore
    private String password;

    private Set<String> roles = new HashSet<>();

}

