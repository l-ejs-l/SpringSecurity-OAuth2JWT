package cl.ejeldes.springsecurity.oauth2jwt.config;

import cl.ejeldes.springsecurity.oauth2jwt.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SpringSecurityConfig(HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository,
                                CustomOAuth2UserService customOAuth2UserService,
                                CustomOidcUserService customOidcUserService,
                                OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                                CustomUserDetailsService userDetailsService,
                                BCryptPasswordEncoder passwordEncoder,
                                JwtAuthenticationProvider jwtAuthenticationProvider,
                                AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOidcUserService = customOidcUserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/api/login")
                    .successHandler(authenticationSuccessHandler)
                    .and()
                .logout().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                    .and()
                .addFilterBefore(new JWTTokenAuthenticationFilter(super.authenticationManager()),UsernamePasswordAuthenticationFilter.class )
                .oauth2Login()
                    .authorizationEndpoint()
                        .authorizationRequestRepository(authorizationRequestRepository)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)
                        .and()
                    .and()
                .csrf().disable()
                .cors().and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();

        //@formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder).and()
                .authenticationProvider(jwtAuthenticationProvider);
    }
}
