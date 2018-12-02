package cl.ejeldes.springsecurity.oauth2jwt.repository;

import cl.ejeldes.springsecurity.oauth2jwt.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by emilio on Nov 22, 2018
 */
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
