package com.everag.mobilemanifest.bootstrap.domain.model.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.SealRemovalReasonStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "description"}, name = "UQ_DESCRIPTION") )
@Audited
@Getter
@Setter
public class SealRemovalReason  extends AuditedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEAL_REMOVAL_REASON_SEQ")
    @SequenceGenerator(name = "SEAL_REMOVAL_REASON_SEQ", sequenceName = "SEAL_REMOVAL_REASON_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable=false)
    @Size(max = 256)
    private String description;

    @NotNull
    private SealRemovalReasonStatus status;

    @Version
    private Integer txLock;

    public SealRemovalReason() {
        super();
    }

    public SealRemovalReason(String description) {
        super();
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SealRemovalReason removalReason = (SealRemovalReason) o;
        if(removalReason.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, removalReason.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SealRemovalReason{" +
                "id=" + id +
                ", description='" + description + "'" +
                '}';
    }
}
