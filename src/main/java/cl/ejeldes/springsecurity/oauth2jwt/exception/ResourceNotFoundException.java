package cl.ejeldes.springsecurity.oauth2jwt.exception;

/**
 * Created by emilio on Nov 13, 2018
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
