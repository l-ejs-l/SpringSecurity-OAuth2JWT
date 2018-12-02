package cl.ejeldes.springsecurity.oauth2jwt.dto.mapper;

import cl.ejeldes.springsecurity.oauth2jwt.dto.UserLogDTO;
import cl.ejeldes.springsecurity.oauth2jwt.entity.UserLog;
import cl.ejeldes.springsecurity.oauth2jwt.entity.security.User;
import cl.ejeldes.springsecurity.oauth2jwt.exception.ResourceNotFoundException;
import cl.ejeldes.springsecurity.oauth2jwt.util.dto.AbstractMapper;
import org.springframework.stereotype.Component;

/**
 * Created by emilio on Nov 22, 2018
 */
@Component
public class UserLogMapper extends AbstractMapper<UserLogDTO, UserLog> {

    @Override
    public UserLogDTO convertToDTO(UserLog userLog) {
        if (userLog == null) {
            logger.debug("UserLogMapper.convertToDTO(): userLog is null");
            throw new ResourceNotFoundException("UserLogMapper.convertToDTO()");
        }

        UserLogDTO dto = new UserLogDTO();
        if (userLog.getUser() != null && userLog.getUser().getEmail() != null)
            dto.setUserEmail(userLog.getUser().getEmail());

        if (userLog.getId() != null) dto.setId(userLog.getId());
        if (userLog.getCreatedAt() != null) dto.setCreatedAt(userLog.getCreatedAt());
        if (userLog.getUser().getId() != null) dto.setUserId(userLog.getUser().getId());

        return dto;
    }

    @Override
    public UserLog convertToEntity(UserLogDTO dto) {
        if (dto == null) {
            logger.debug("UserLogMapper.convertToEntity(): userLogDTO is null");
            throw new ResourceNotFoundException("UserLogMapper.convertToEntity()");
        }

        UserLog userLog = new UserLog();
        if (dto.getCreatedAt() != null) userLog.setCreatedAt(dto.getCreatedAt());
        if (dto.getUserId() != null) userLog.setUser(new User());
        return null;
    }
}
