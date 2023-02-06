package com.everag.mobilemanifest.bootstrap.domain.model.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "description" }, name = "UQ_SAMPLEPURPOSE_DESC") )
@Audited(targetAuditMode= RelationTargetAuditMode.NOT_AUDITED)
@Getter
@Setter
public class SamplePurpose  extends AuditedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_PURPOSE_SEQ")
    @SequenceGenerator(name = "SAMPLE_PURPOSE_SEQ", sequenceName = "SAMPLE_PURPOSE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable=false, length=255)
    @Size(max=255)
    private String description;

    @Version
    private Integer txLock;

    public SamplePurpose() {
        super();
    }

    /*
     * Required args constructor
     */
    public SamplePurpose(String description) {
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
        SamplePurpose samplePurpose = (SamplePurpose) o;
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
