package cl.ejeldes.springsecurity.oauth2jwt.config;

import cl.ejeldes.springsecurity.oauth2jwt.security.CustomOAuth2UserService;
import cl.ejeldes.springsecurity.oauth2jwt.security.CustomOidcUserService;
import cl.ejeldes.springsecurity.oauth2jwt.security.HttpCookieOAuth2AuthorizationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by emilio on Nov 12, 2018
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Autowired
    public SpringSecurityConfig(HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository,
                                CustomOAuth2UserService customOAuth2UserService,
                                CustomOidcUserService customOidcUserService) {
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOidcUserService = customOidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .authorizationRequestRepository(authorizationRequestRepository)
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)
                        .and()
                    .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();

        //@formatter:on
    }
}
