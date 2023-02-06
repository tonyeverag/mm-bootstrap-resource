package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_OWNER_TANKID", columnNames = { "owned_by_id", "tankerId" }) })
@Audited
@Getter
@Setter
public class Tanker extends AuditedEntity implements Serializable, Barcodeable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TANKER_SEQ")
    @SequenceGenerator(name = "TANKER_SEQ", sequenceName = "TANKER_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    private EntityStatus status;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String tankerId;

    @Version
    private Integer txLock;

    @ManyToOne
    private Company ownedBy;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "TANKER_BARCODE",
            joinColumns = @JoinColumn(name="tanker_id", referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name="barcode_id", referencedColumnName="ID"))
    private Set<Barcode> barcodes = new HashSet<>();

    public Tanker() {
        super();
    }

    /*
     * Required Args constructor
     */
    public Tanker(EntityStatus status, String tankerId) {
        super();
        this.status = status;
        this.tankerId = tankerId;
    }

    public Tanker(EntityStatus status, String tankerId, Company tankerOwner) {
        this(status, tankerId);
        this.ownedBy = tankerOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tanker tanker = (Tanker) o;
        if (tanker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tanker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tanker{" +
                "id=" + id +
                ", status=" + status +
                ", tankerId='" + tankerId + '\'' +
                ", txLock=" + txLock +
                ", ownedBy=" + ownedBy +
                ", barcodes=" + barcodes +
                '}';
    }
}
