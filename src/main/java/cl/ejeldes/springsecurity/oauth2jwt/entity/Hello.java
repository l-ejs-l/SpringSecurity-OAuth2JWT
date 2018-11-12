package cl.ejeldes.springsecurity.oauth2jwt.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by emilio on Nov 12, 2018
 */
@Data
public class Hello implements Serializable {

    private static final long serialVersionUID = 6148273506288982170L;
    private String message;
    private String from;
    private String to;
    private Date createAt;

    public Hello() {
        this.createAt = new Date();
    }
}
