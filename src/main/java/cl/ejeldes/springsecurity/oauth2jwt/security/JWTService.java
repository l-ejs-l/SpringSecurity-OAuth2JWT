package cl.ejeldes.springsecurity.oauth2jwt.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by emilio on Nov 13, 2018
 */
public interface JWTService {

    String createToken(String audience, String subject, Long expirationMillis, Map<String, Object> claimMap) throws
                                                                                                             JOSEException;
    String createToken(String audience, String subject, Long expirationMillis) throws JOSEException;

    JWTClaimsSet parseToken(String token) throws JOSEException, ParseException;
}
