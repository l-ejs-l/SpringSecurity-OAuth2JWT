package cl.ejeldes.springsecurity.oauth2jwt.security;

import cl.ejeldes.springsecurity.oauth2jwt.security.util.SecurityUtils;
import cl.ejeldes.springsecurity.oauth2jwt.util.GenericUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by emilio on Nov 12, 2018
 */
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String REDIRECT_URI_COOKIE_PARAM_NAME = "redirect_uri";
    private static final String AUTHORIZATION_REQUEST_ATTR_NAME = "AUTHORIZATION_REQUEST";

    private int cookieExpiracySecs = 120;

    public static void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_REQUEST_ATTR_NAME) || cookie.getName().equals(
                        REDIRECT_URI_COOKIE_PARAM_NAME)) {

                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }

            }
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "Request cannot be null");
        return SecurityUtils.fetchCookie(request, AUTHORIZATION_REQUEST_ATTR_NAME)
                .map(this::deserialize)
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                         HttpServletResponse response) {

        Assert.notNull(request, "Request cannot be null");
        Assert.notNull(response, "Response cannot be null");

        if (authorizationRequest == null) {
            deleteCookies(request, response);
            return;
        }

        Cookie cookie = new Cookie(AUTHORIZATION_REQUEST_ATTR_NAME, GenericUtils.serialize(authorizationRequest));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpiracySecs);
        response.addCookie(cookie);

        String redirectURI = request.getParameter(REDIRECT_URI_COOKIE_PARAM_NAME);
        if (redirectURI != null && !redirectURI.equals("")) {
            cookie = new Cookie(REDIRECT_URI_COOKIE_PARAM_NAME, redirectURI);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(cookieExpiracySecs);
            response.addCookie(cookie);
        }


    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        return loadAuthorizationRequest(request);
    }

    private OAuth2AuthorizationRequest deserialize(Cookie cookie) {
        return GenericUtils.deserialize(cookie.getValue());
    }
}
