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
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public Hello publicHello() {
        return getHello("This is a public message", "The public");
    }

    @GetMapping("/private")
    public Hello privateHello() {
        return getHello("This is a private message", "A private user");
    }

    @GetMapping("/admin")
    public Hello adminHello() {
        return getHello("This is a message for the admin", "The admin");
    }

    @GetMapping("/user")
    public UserDTO getPrincipal() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserDTO();
    }

    private Hello getHello(String s, String s2) {
        Hello hello = new Hello();
        hello.setMessage(s);
        hello.setFrom("The controller");
        hello.setTo(s2);

        return hello;
    }

}
