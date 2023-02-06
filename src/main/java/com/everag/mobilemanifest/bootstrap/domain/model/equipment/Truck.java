package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_OWNER_TRUCKID", columnNames = { "owned_by_id", "truckId" }) })
@Audited
@Getter
@Setter
public class Truck extends AuditedEntity implements Serializable, Barcodeable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRUCK_SEQ")
    @SequenceGenerator(name = "TRUCK_SEQ", sequenceName = "TRUCK_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    private EntityStatus status;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String truckId;

    @Version
    private Integer txLock;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "TRUCK_BARCODE",
            joinColumns = @JoinColumn(name="truck_id", referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name="barcode_id", referencedColumnName="ID"))
    private Set<Barcode> barcodes = new HashSet<>();

    @ManyToOne
    private Company ownedBy;

    public Truck() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Truck(EntityStatus status, String truckNumber) {
        super();
        this.status = status;
        this.truckId = truckNumber;
    }

    public Truck(EntityStatus status, String truckNumber, Company truckOwner) {
        this(status, truckNumber);
        this.ownedBy = truckOwner;
    }

    public Truck(EntityStatus status, String truckId, Company truckOwner, Barcode barcode) {
        this(status, truckId, truckOwner);
        this.barcodes.add(barcode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Truck truck = (Truck) o;
        if (truck.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Truck{" + "id=" + id + ", truckId='" + truckId + "'" + '}';
    }

    @Override
    public void setBarcodes(Set<Barcode> barcodes) {

        if(this.barcodes != null && barcodes != null) {
            this.barcodes.clear();
            this.barcodes.addAll(barcodes);
        }
    }
}
