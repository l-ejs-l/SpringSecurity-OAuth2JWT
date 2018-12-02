package cl.ejeldes.springsecurity.oauth2jwt.entity;

import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

/**
 * Created by emilio on Nov 22, 2018
 */

@Getter
@Setter
@Entity
@Table(name = "user_logs")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLog extends BaseEntity<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
