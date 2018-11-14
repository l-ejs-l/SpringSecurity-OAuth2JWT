package cl.ejeldes.springsecurity.oauth2jwt.entity.security;

import cl.ejeldes.springsecurity.oauth2jwt.util.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by emilio on Nov 13, 2018
 */
@Entity
@Getter
@Setter
@Table(name = "authorities")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity<Long> {

    @Column(unique = true)
    private String authority;
}
