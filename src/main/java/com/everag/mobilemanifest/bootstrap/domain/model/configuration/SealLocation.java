package com.everag.mobilemanifest.bootstrap.domain.model.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(name="UQ_SEAL_LOCATION_DESCRIPTION", columnNames = { "description" })})
@Audited
@Getter
@Setter
public class SealLocation extends AuditedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEAL_LOCATION_SEQ")
    @SequenceGenerator(name = "SEAL_LOCATION_SEQ", sequenceName = "SEAL_LOCATION_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 255)
    @Size(max = 255)
    private String description;

    @Version
    private Integer txLock;

    public SealLocation() {
        super();
    }

    /*
     * Required args constructor
     */
    public SealLocation(String description) {
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
        SealLocation samplePurpose = (SealLocation) o;
        if(samplePurpose.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, samplePurpose.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SamplePurpose{" +
                "id=" + id +
                ", description='" + description + "'" +
                '}';
    }
}
