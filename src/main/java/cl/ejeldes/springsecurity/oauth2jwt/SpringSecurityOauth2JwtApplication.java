package cl.ejeldes.springsecurity.oauth2jwt;

import cl.ejeldes.springsecurity.oauth2jwt.security.JWTService;
import cl.ejeldes.springsecurity.oauth2jwt.security.JWTSignedServiceImpl;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SpringSecurityOauth2JwtApplication {

    public static void main(String[] args) throws JOSEException, ParseException {
        SpringApplication.run(SpringSecurityOauth2JwtApplication.class, args);

        Logger logger = LoggerFactory.getLogger(SpringSecurityOauth2JwtApplication.class);

        JWTService jwtService = new JWTSignedServiceImpl();

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        Map<String, Object> alice = new HashMap<>();
        alice.put("authorities", roles);

        String token1 = jwtService.createToken("alice", "alice", 120000L, alice);
        logger.info("JWService TOKEN: \n" + token1);

        logger.info("JWTService parsed: \n" + jwtService.parseToken(token1).toJSONObject());

    }
}
