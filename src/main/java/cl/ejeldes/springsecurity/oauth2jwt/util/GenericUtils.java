package cl.ejeldes.springsecurity.oauth2jwt.util;

import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Base64;

/**
 * Created by emilio on Nov 12, 2018
 */
public class GenericUtils {

    /**
     * Serializes an object
     */
    public static String serialize(Serializable obj) {

        return new String(Base64.getUrlEncoder().encode(SerializationUtils.serialize(obj)));
    }

    /**
     * Deserializes an object
     */
    public static <T> T deserialize(String serializedObj) {

        return (T) SerializationUtils.deserialize(Base64.getUrlDecoder().decode(serializedObj));
    }
}
