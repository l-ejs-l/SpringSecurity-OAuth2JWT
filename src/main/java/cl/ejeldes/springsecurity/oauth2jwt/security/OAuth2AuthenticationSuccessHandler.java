package cl.ejeldes.springsecurity.oauth2jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by emilio on Nov 15, 2018
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    private final JWTService jwtService;

    public OAuth2AuthenticationSuccessHandler(JWTService jwtService) {
        this.jwtService = jwtService;
        logger.info("OAuth2AuthenticationSuccessHandler :: Created");
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {

        return super.determineTargetUrl(request, response);
    }
}
