package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Audited
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_HAULER_COMPANY", columnNames = { "company_id" }) })
@Getter
@Setter
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "graph.HaulingCompany.company",
                attributeNodes = @NamedAttributeNode(value = "company") )
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = HaulingCompany.class)

public class HaulingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HAULING_COMPANY_SEQ")
    @SequenceGenerator(name = "HAULING_COMPANY_SEQ", sequenceName = "HAULING_COMPANY_SEQ", initialValue = 1000, allocationSize = 1)
    private Long id;

    @OneToOne(optional = false)
    private Company company;

    @Version
    private Integer txLock;

    @NotNull
    private Boolean supportRoutesLinking = false;

    @NotNull
    private Boolean paperManifestEnabled = false;

    @NotNull
    private Boolean supportTruckTracking = false;

    @NotNull
    private Boolean supportMultiTankPickups = false;

    @NotNull
    private Boolean supportSplitTanker = false;

    @NotNull
    private Boolean supportLongHaulResponsible = false;

    @Column(length = 10)
    private String licenseNumber;

    public HaulingCompany() {
        super();
    }

    /*
     * Required args constructor
     */
    public HaulingCompany(Company company,Boolean supportTruckTracking) {
        super();
        this.company = company;
        this.supportTruckTracking = supportTruckTracking;
    }

    /*
     * 	Only for Routes with company name only
     * */
    public HaulingCompany(Company company) {
        super();
        this.supportRoutesLinking = null;
        this.paperManifestEnabled = null;
        this.supportTruckTracking = null;
        this.supportMultiTankPickups = null;
        this.supportSplitTanker = null;
        this.supportLongHaulResponsible = null;
        this.company = company;
    }

    public String getName() {
        return company.getName();
    }

    public Boolean isSupportRoutesLinking() {
        return this.supportRoutesLinking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HaulingCompany haulingCompany = (HaulingCompany) o;
        if(haulingCompany.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, haulingCompany.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        }
        else {
            return super.hashCode();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Company: ").append(getCompany());
        return sb.toString();
    }
}
