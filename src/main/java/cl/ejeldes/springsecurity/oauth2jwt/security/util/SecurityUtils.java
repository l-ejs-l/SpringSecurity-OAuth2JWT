package cl.ejeldes.springsecurity.oauth2jwt.security.util;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by emilio on Nov 12, 2018
 */
@Component
public class SecurityUtils {

    public static Optional<Cookie> fetchCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(name))
                    return Optional.of(cookie);

        return Optional.empty();
    }
}
