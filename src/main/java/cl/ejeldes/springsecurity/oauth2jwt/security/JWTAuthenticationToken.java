package cl.ejeldes.springsecurity.oauth2jwt.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by emilio on Nov 15, 2018
 */
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private UserDetails principal;
    private String jwtToken;

    public JWTAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.jwtToken = token;
    }

    public JWTAuthenticationToken(UserDetails principal, String jwtToken,
                                  Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.principal = principal;
        this.jwtToken = jwtToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
