package cl.ejeldes.springsecurity.oauth2jwt.util.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by emilio on Nov 13, 2018
 */
public abstract class AbstractMapper<DTO, ENTITY> implements Mapper<DTO, ENTITY> {

    protected Logger logger = LoggerFactory.getLogger(AbstractMapper.class);

}
