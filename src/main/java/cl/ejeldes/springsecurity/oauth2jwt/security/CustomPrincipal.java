package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a custom <code>UserDetails</code>, <code>OidcUser</code> which extends from <code>OAuth2User</code> and
 * contains a <code>UserDTO</code> to be used all across the framework
 * <p>
 * Created by emilio on Nov 15, 2018
 */
@Getter
@Setter
@Component
public class CustomPrincipal implements UserDetails, OidcUser, CredentialsContainer {

    private static final long serialVersionUID = -7849730155307434535L;

    private final UserDTO userDTO;
    private Map<String, Object> attributes;
    private String name;
    private Map<String, Object> claims;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;


    public CustomPrincipal(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public void eraseCredentials() {
        userDTO.setPassword(null);
        attributes = null;
        claims = null;
        userInfo = null;
        idToken = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDTO.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        String str = "PRINCIPAL: \n" +
                "USER DTO: \n" + userDTO.toString() + "\n" +
                "NAME: " + name + "\n";

        Set<String> keys = attributes.keySet();
        StringBuilder attrString = new StringBuilder("ATRIBUTES: \n");

        for (String key : keys) {
            String o = (String) attributes.get(key);
            attrString.append(o).append("\n");
        }

        return str + attrString.toString();

    }

}
