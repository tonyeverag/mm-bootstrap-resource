package com.everag.mobilemanifest.bootstrap.domain.model.security;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Employment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("DRIVER")
@Audited
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = SysUser.class)
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "driverWithEmployers",
                attributeNodes = {
                        @NamedAttributeNode(value = "employers", subgraph="employment"),
                        @NamedAttributeNode("samplerLicences")
                },
                subgraphs = {
                        @NamedSubgraph(name = "employment",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "hauler", subgraph="hauler")
                                }),
                        @NamedSubgraph(name = "hauler", attributeNodes = {
                                @NamedAttributeNode(value = "company", subgraph="company")
                        }),
                        @NamedSubgraph(name = "company", attributeNodes = {
                                @NamedAttributeNode(value = "address")
                        })
                }
        )
})
public class DriverUser extends TransporterUser {
    public DriverUser() {
        super();
    }

    public DriverUser(String firstName, String lastName, String username, String email, Set<Employment> employers, boolean receivesNotifications){
        super(firstName, lastName, username, email, receivesNotifications);
        this.employers = employers;
    }

    public static final String DRIVER_USER_ROLE = "Driver Role";

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "driver")
    @Size(min = 1)
    private Set<Employment> employers = new HashSet<>();

    @Override
    public BasicAuthorityType authorityRequired() {
        return BasicAuthorityType.CONFIGURATION_DRIVER_USER;
    }

    @Override
    public String standardAuthority() {
        return "ROLE_DRIVER";
    }

    public void setEmployers(Set<Employment> employers) {
        this.employers = employers;
        if (employers != null) {
            this.employers.forEach((employer) -> {
                employer.setDriver(this);
                if (employer.getStartDate() == null) employer.setStartDate(LocalDate.now());
            });
        }
    }

    public void addEmployer(Employment employer) {
        employer.setDriver(this);
        employers.add(employer);
    }

    @AssertTrue(message = "Driver must have at least one hauler")
    public boolean isEmployed() {
        return employers.stream().anyMatch(e -> e.getEndDate() == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriverUser driverUser = (DriverUser) o;
        if(driverUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverUser.getId());
    }
}
