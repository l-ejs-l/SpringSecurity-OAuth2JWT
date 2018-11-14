package cl.ejeldes.springsecurity.oauth2jwt.security;

import java.util.Map;

/**
 * Created by emilio on Nov 13, 2018
 */
public interface JWTService {

    String createToken(String audience, String subject, Long expirationMillis, Map<String, Object> claimMap);

    String createToken(String audience, String subject, Long expirationMillis);
}
