package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tanker;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Truck;
import com.everag.mobilemanifest.bootstrap.domain.model.location.DropYard;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Plant;
import com.everag.mobilemanifest.bootstrap.domain.model.location.PostalAddress;
import com.everag.mobilemanifest.bootstrap.domain.model.security.SecuredInstance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }, name = "UQ_COMPANY_NAME") )
@Audited
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Company.trucksAndTankers",
                attributeNodes = {@NamedAttributeNode("address"),
                        @NamedAttributeNode("trucks"),
                        @NamedAttributeNode("tankers")})
})
public class Company  extends AuditedEntity implements SecuredInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ")
    @SequenceGenerator(name = "COMPANY_SEQ", sequenceName = "COMPANY_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Max(value = Long.MAX_VALUE)
    private Long companyNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PostalAddress address;

    @Version
    private Integer txLock;

    @OneToMany(mappedBy = "owningCompany")
    @JsonIgnore
    private Set<Plant> plants = new HashSet<>();

    @OneToMany(mappedBy = "owningCompany")
    @JsonIgnore
    private Set<DropYard> dropYards = new HashSet<DropYard>();

    @OneToMany(mappedBy = "ownedBy", fetch=FetchType.LAZY)
    private Set<Tanker> tankers = new HashSet<>();

    @OneToMany(mappedBy = "ownedBy", fetch=FetchType.LAZY)
    private Set<Truck> trucks = new HashSet<>();


    public Company() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Company(String name, PostalAddress address) {
        super();
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return getName();
    }

    @PostPersist
    public void aftersave() {
        setCompanyNumber(getId());
    }
}
