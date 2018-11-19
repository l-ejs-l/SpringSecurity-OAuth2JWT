package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by emilio on Nov 16, 2018
 */
public class JWTTokenAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JWTTokenAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    public JWTTokenAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        logger.debug("INSIDE JWTTokenAuthenticationFilter ...");

        if (tokenPresent(request)) {
            logger.debug("Found a token");

            String token = request.getHeader(SecurityUtils.TOKEN_REQUEST_HEADER_NAME).substring(7);
            JWTAuthenticationToken authRequest = new JWTAuthenticationToken(token);

            try {

                Authentication auth = authenticationManager.authenticate(authRequest);
                SecurityContextHolder.getContext().setAuthentication(auth);

                logger.debug("Token authentication successful");

            } catch (Exception e) {

                logger.debug("Token authentication failed - " + e.getMessage());

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                   "Authentication Failed: " + e.getMessage());

                return;
            }
        } else
            logger.debug("Token authentication skipped");

        filterChain.doFilter(request, response);
    }

    protected boolean tokenPresent(HttpServletRequest request) {

        String header = request.getHeader(SecurityUtils.TOKEN_REQUEST_HEADER_NAME);
        return header != null && header.startsWith(SecurityUtils.TOKEN_PREFIX);
    }


}
