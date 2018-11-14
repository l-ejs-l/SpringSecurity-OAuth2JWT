package cl.ejeldes.springsecurity.oauth2jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class SpringSecurityOauth2JwtApplication {

    public static void main(String[] args) throws JOSEException, BadJOSEException, ParseException {
        SpringApplication.run(SpringSecurityOauth2JwtApplication.class, args);
        Logger logger = LoggerFactory.getLogger(SpringSecurityOauth2JwtApplication.class);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("email", "sanjay@example.com")
                .claim("name", "Sanjay Patel")
                .build();

        logger.info("Claims created");

        Payload payload = new Payload(claims.toJSONObject());
        logger.info("Payload created");


        JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
        logger.info("JWEHeader created");

        String secret = "841D8A6C80CBA4FCAD32D5367C18C53B";
        byte[] secretKey = secret.getBytes();
        DirectEncrypter encrypter = new DirectEncrypter(secretKey);

        JWEObject jweObject = new JWEObject(header, payload);
        jweObject.encrypt(encrypter);
        String token = jweObject.serialize();
        logger.info("JWE TOKEN CREATED: " + token);

        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();

        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKey);
        JWEKeySelector<SimpleSecurityContext> jweKeySelector =
                new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256,
                                                                    jweKeySource);
        jwtProcessor.setJWEKeySelector(jweKeySelector);

        JWTClaimsSet parsedClaims = jwtProcessor.process(token, null);
        String email = (String) parsedClaims.getClaim("email");
        String name = (String) parsedClaims.getClaim("name");

        logger.info("JWE: NAME: " + name);
        logger.info("JWE: EMAIL" + email);

    }
}
