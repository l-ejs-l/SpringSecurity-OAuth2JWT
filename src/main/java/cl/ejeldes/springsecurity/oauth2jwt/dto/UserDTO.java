package cl.ejeldes.springsecurity.oauth2jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by emilio on Nov 13, 2018
 */
@Data
@Component
public class UserDTO {

    private Long id;
    private String username;
    private String name;

    @JsonIgnore
    private String password;

    private Set<String> roles = new HashSet<>();

}

