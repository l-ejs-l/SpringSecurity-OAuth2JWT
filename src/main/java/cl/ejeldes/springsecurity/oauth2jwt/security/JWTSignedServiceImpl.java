package cl.ejeldes.springsecurity.oauth2jwt.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by emilio on Nov 14, 2018
 */
@Service
public class JWTSignedServiceImpl implements JWTService {

    @Value("${jwt.key}")
    private String secret;
    private MACSigner signer;
    private MACVerifier verifier;

    private JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

    public JWTSignedServiceImpl() throws KeyLengthException {
        SecureRandom random = new SecureRandom();
        if (secret == null || secret.equals("")) secret = UUID.randomUUID().toString();
        byte[] secretKey = secret.getBytes();
        random.nextBytes(secretKey);
        try {
            signer = new MACSigner(secretKey);
            verifier = new MACVerifier(secretKey);
        } catch (KeyLengthException e) {
            throw new KeyLengthException("JWT key is too long");
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createToken(String audience, String subject, Long expirationMillis,
                              Map<String, Object> claimMap) {

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

        builder
                .issueTime(new Date())
                .subject(subject)
                .expirationTime(new Date(System.currentTimeMillis() + expirationMillis))
                .audience(audience);

        // adding claimMap
        claimMap.forEach(builder::claim);

        // claims
        JWTClaimsSet claims = builder.build();

        // JWT
        SignedJWT signedJWT = new SignedJWT(header, claims);

        try {
            // sign
            signedJWT.sign(signer);
            // serialize
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String createToken(String audience, String subject, Long expirationMillis) throws JOSEException {
        return createToken(audience, subject, expirationMillis, new HashMap<>());
    }

    @Override
    public JWTClaimsSet parseToken(String token) {


        try {
            SignedJWT parsed = SignedJWT.parse(token);

            // if valid token
            if (parsed.verify(verifier)) {
                return parsed.getJWTClaimsSet();
            }
            // else throws
            throw new BadCredentialsException("Bad credentials");

        } catch (ParseException | JOSEException e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    //        // Create JWE object with signed JWT as payload
//        JWEObject jweObject = new JWEObject(
//                new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
//                        .contentType("JWT") // required to indicate nested JWT
//                        .build(),
//                new Payload(signedJWT));
//
//        // Encrypt with the recipient's public key
//        jweObject.encrypt(new RSAEncrypter(recipientPublicJWK));
//
//        // Serialise to JWE compact form
//        String jweString = jweObject.serialize();
//
//        logger.info("JWE: " + jweString);
}
