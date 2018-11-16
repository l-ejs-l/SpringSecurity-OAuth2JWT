package cl.ejeldes.springsecurity.oauth2jwt.entity.security;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserDTO;
import cl.ejeldes.springsecurity.oauth2jwt.util.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;

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

    @NotBlank
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

    public UserDTO toUserDTO() {
        UserDTO userDTO = new UserDTO();

        userDTO.setPassword(password);
        userDTO.setUsername(email);
        userDTO.setRoles(roles.stream().map(Role::getAuthority).collect(Collectors.toSet()));
        userDTO.setId(getId());

        return userDTO;
    }
}
