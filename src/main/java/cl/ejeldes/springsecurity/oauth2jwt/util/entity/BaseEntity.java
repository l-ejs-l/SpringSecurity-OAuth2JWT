package cl.ejeldes.springsecurity.oauth2jwt.util.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by emilio on Nov 12, 2018
 */
@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"createdById", "lastModifiedById", "createdAt", "lastModifiedDate", "new"})
public class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> {

    private static final long serialVersionUID = -8151190931948396443L;

    @CreatedBy
    @Column(name = "created_by_id")
    private ID createdById;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.lastModifiedDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = new Date();
    }

}
