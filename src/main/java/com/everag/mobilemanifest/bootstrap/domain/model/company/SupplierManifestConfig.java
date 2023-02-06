package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Audited
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_SUPPLIER_MANIFEST_CONFIG", columnNames = { "supplier_id" }) })
@Getter
@Setter
public class SupplierManifestConfig extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SUPPLIER_MANIFEST_CONFIG_SEQ")
    @SequenceGenerator(name="SUPPLIER_MANIFEST_CONFIG_SEQ", sequenceName="SUPPLIER_MANIFEST_CONFIG_SEQ", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Version
    private Integer txLock;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private SupplyCompany supplier;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer currentVal;

    @Min(value=1)
    @Max(value=2147483647)
    private Integer max;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer min;

    @Length(min=1, max=1)
    private String padCharacter;

    @Min(value=1)
    @Max(value=2147483647)
    private Integer length;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Supplier: ");
        if (supplier != null) {
            sb.append(supplier.getName()).append(", ");
        }
        sb.append("Current Val: ").append(getCurrentVal()).append(", ");
        sb.append("Min: ").append(getMin()).append(", ");
        sb.append("Max: ").append(getMax()).append(", ");
        sb.append("Pad Character: ").append(getPadCharacter()).append(", ");
        sb.append("Length: ").append(getLength());

        return sb.toString();
    }
}
