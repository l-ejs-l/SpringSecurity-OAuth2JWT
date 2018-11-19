package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import com.nimbusds.jwt.JWTClaimsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by emilio on Nov 16, 2018
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    public JwtAuthenticationProvider(JWTService jwtService,
                                     CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;

        logger.debug("JwtAuthenticationProvider created");
    }

    @Override
    @Transactional(readOnly = true)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.debug("Authenticating ...");

        String token = (String) authentication.getCredentials();

        JWTClaimsSet claims = jwtService.parseToken(token);

        String username = claims.getSubject();
        User user = userDetailsService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        logger.debug("User found ...");

        CustomPrincipal principal = new CustomPrincipal(user.toUserDTO());
        return new JWTAuthenticationToken(principal, token, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
