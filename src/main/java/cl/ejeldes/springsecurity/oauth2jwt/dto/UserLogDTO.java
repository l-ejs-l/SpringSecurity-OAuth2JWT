package cl.ejeldes.springsecurity.oauth2jwt.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by emilio on Nov 22, 2018
 */
@Component
@Data
public class UserLogDTO {

    private Long id;
    private Date createdAt;
    private String userEmail;
    private Long userId;
}
