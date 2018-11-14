package cl.ejeldes.springsecurity.oauth2jwt.util.dto;

/**
 * Created by emilio on Nov 13, 2018
 */
public interface Mapper<DTO, ENTITY> {

    DTO convertToDTO(ENTITY entity);

    ENTITY convertToEntity(DTO dto);
}
