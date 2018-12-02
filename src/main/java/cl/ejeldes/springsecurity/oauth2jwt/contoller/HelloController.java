package cl.ejeldes.springsecurity.oauth2jwt.contoller;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.entity.Hello;
import cl.ejeldes.springsecurity.oauth2jwt.security.CustomPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by emilio on Nov 12, 2018
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/user")
    public UserDTO getUser() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserDTO();
    }

    @GetMapping("/user/times-logged")
    public Integer getTimesLoggedIn() {
        return 1;
    }

}
