package cl.ejeldes.springsecurity.oauth2jwt.config;

import cl.ejeldes.springsecurity.oauth2jwt.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by emilio on Nov 13, 2018
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    private final SecurityUtils.Cors cors = new SecurityUtils.Cors();

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(cors.getAllowedOrigins())
                .allowedMethods(cors.getAllowedMethods())
                .allowedHeaders(cors.getAllowedHeaders())
                .exposedHeaders(cors.getExposedHeaders())
                .allowCredentials(true)
                .maxAge(cors.getMaxAge());
    }
}
