package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.util.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by emilio on Nov 17, 2018
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTService jwtService;
    private ObjectMapper objectMapper;
    private long expirationMillis = 864000000L; // 10 days

    public AuthenticationSuccessHandler(JWTService jwtService) {this.jwtService = jwtService;}

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String expirationMillisStr = request.getParameter("expirationMillis");

        UserDTO userDTO = SecurityUtils.currentUser();

        addAuthHeader(response, userDTO.getUsername(), expirationMillis);

        // as done in the base class
        clearAuthenticationAttributes(request);
    }

    /**
     * Adds a Lemon-Authorization header to the response
     */
    public void addAuthHeader(HttpServletResponse response, String username, Long expirationMillis) {

        response.addHeader(SecurityUtils.TOKEN_RESPONSE_HEADER_NAME,
                           SecurityUtils.TOKEN_PREFIX +
                                   jwtService.createToken("auth", username, expirationMillis));
    }
}
