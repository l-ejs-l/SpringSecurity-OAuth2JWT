package cl.ejeldes.springsecurity.oauth2jwt.util;


import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.security.CustomPrincipal;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by emilio on Nov 12, 2018
 */
@Component
public class SecurityUtils {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_REQUEST_HEADER_NAME = "Authorization";
    public static final String TOKEN_RESPONSE_HEADER_NAME = "Lemon-Authorization";

    public static Optional<Cookie> fetchCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals(name))
                    return Optional.of(cookie);

        return Optional.empty();
    }

    public static UserDTO currentUser() {

        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserDTO();
    }

    public static UserDTO currentUser(Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomPrincipal) {
                return ((CustomPrincipal) principal).getUserDTO();
            }
        }
        return null;
    }

    /**
     * Cors configuration
     */
    @Data
    public static class Cors {

        /**
         * Comma separated whitelisted URLs for CORS. Should contain the applicationURL at the minimum. Not providing
         * this property would disable CORS configuration.
         */
        private String[] allowedOrigins = {"http://localhost:3000", "192.168.0.25:3000"};

        /**
         * Methods to be allowed, e.g. GET,POST,...
         */
        private String[] allowedMethods = {"GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "PATCH"};

        /**
         * Request headers to be allowed, e.g. content-type,accept,origin,x-requested-with,...
         */
        private String[] allowedHeaders = {
                "Accept",
                "Accept-Encoding",
                "Accept-Language",
                "Cache-Control",
                "Connection",
                "Content-Length",
                "Content-Type",
                "Cookie",
                "Host",
                "Origin",
                "Pragma",
                "Referer",
                "User-Agent",
                "x-requested-with",
                TOKEN_REQUEST_HEADER_NAME};

        /**
         * Response headers that you want to expose to the client JavaScript programmer, e.g. Lemon-Authorization. I
         * don't think we need to mention here the headers that we don't want to access through JavaScript. Still, by
         * default, we have provided most of the common headers.
         *
         * <br>
         * See <a href="http://stackoverflow.com/questions/25673089/why-is-access-control-expose-headers-needed#answer-25673446">
         * here</a> to know why this could be needed.
         */
        private String[] exposedHeaders = {
                "Cache-Control",
                "Connection",
                "Content-Type",
                "Date",
                "Expires",
                "Pragma",
                "Server",
                "Set-Cookie",
                "Transfer-Encoding",
                "X-Content-Type-Options",
                "X-XSS-Protection",
                "X-Frame-Options",
                "X-Application-Context",
                TOKEN_RESPONSE_HEADER_NAME};

        /**
         * CORS <code>maxAge</code> long property
         */
        private long maxAge = 3600L;
    }


}
