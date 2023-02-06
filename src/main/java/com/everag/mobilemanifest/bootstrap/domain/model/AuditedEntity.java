package com.everag.mobilemanifest.bootstrap.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class AuditedEntity {
    private static final Logger log = LoggerFactory.getLogger(AuditedEntity.class);

    @JsonIgnore
    @DateTimeFormat(pattern="SS")
    private LocalDateTime creationDateTime = null;

    @JsonIgnore
    @DateTimeFormat(pattern="SS")
    private LocalDateTime lastUpdatedDateTime = null;

    @PrePersist
    public void createAuditInfo() {
        setCreationDateTime(LocalDateTime.now());
        setLastUpdatedDateTime(getCreationDateTime());
        log.debug("PrePersist:Create Audit Info: {}", this.getClass().getName());
    }

    @PreUpdate
    public void updateAuditInfo() {
        setLastUpdatedDateTime(LocalDateTime.now());
        log.debug("PreUpdate:Update Audit Info: {}", this.getClass().getName());
    }
}
