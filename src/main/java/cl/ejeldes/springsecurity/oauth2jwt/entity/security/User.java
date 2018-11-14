package cl.ejeldes.springsecurity.oauth2jwt.entity.security;

import cl.ejeldes.springsecurity.oauth2jwt.util.entity.BaseEntity;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Created by emilio on Nov 12, 2018
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity<Long> {

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    @Nullable
    private String password;

    private Boolean enable = true;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )
    private Set<Role> roles;
}
