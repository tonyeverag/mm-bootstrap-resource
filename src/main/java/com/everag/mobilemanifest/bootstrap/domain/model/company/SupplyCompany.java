package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.BarcodeType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_SUPPLIER_COMPANY", columnNames = { "company_id" }) })
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "SupplyCompany.divisions", attributeNodes = @NamedAttributeNode("divisions"))
})
public class SupplyCompany extends AuditedEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CONFIGURABLE_ENTITY_SEQ")
    @SequenceGenerator(name="CONFIGURABLE_ENTITY_SEQ", sequenceName="CONFIGURABLE_ENTITY_SEQ", initialValue = 1000, allocationSize = 1)
    private Long id;

    @OneToOne(optional=false)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplyCompany", orphanRemoval = true, fetch=FetchType.LAZY)
    @JsonManagedReference("supplyCompanyDivisions")
    private Set<Division> divisions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "supplier", orphanRemoval = true, fetch=FetchType.LAZY)
    private SupplierManifestConfig manifestConfig;

    private Boolean enableAutogenManifest;

    @NotNull
    private Boolean generateBarcode = false;

    @NotNull
    private Boolean enableSampleValidation = false;

    @NotNull
    private Integer maxSampleNumberSize = 0;

    @NotNull
    private Integer minSampleNumberSize = 0;

    @Enumerated(EnumType.ORDINAL)
    private BarcodeType barcodeType;

    @NotNull
    private Boolean enableRequireSamples = false;

    @NotNull
    private Boolean requireScaleImageOnPickup = false;

    @Size(max = 1023)
    private String notificationDisclaimer;

    @Version
    private Integer txLock;

    public SupplyCompany() {
        super();
    }

    /*
     * Required-args constructor
     */
    public SupplyCompany(Company company, Boolean enableAutogenManifest) {
        super();
        this.company = company;
        this.enableAutogenManifest = enableAutogenManifest;
    }

    public String getName() {
        return this.company.getName();
    }

    public void setDivisions(Set<Division> divisions) {
        this.divisions = divisions;
        this.divisions.forEach((division) -> {
            division.setSupplyCompany(this);
        });
    }

    public void addDivision(Division division) {
        division.setSupplyCompany(this);
        divisions.add(division);
    }

    public void setManifestConfig(SupplierManifestConfig manifestConfig) {
        if(manifestConfig != null && manifestConfig.getSupplier() == null){
            manifestConfig.setSupplier(this);
        }
        this.manifestConfig = manifestConfig;
    }

    @PrePersist
    private void beforeCreate() {
        if (this.barcodeType == null) {
            this.barcodeType = BarcodeType.INTER2OF5;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId());
        sb.append("Name: ");
        if (company != null) {
            sb.append(company.getName());
        }

        return sb.toString();
    }
}
