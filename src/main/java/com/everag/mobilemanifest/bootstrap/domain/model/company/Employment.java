package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.security.DriverUser;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Audited
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Employment.class)
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYMENT_SEQ")
    @SequenceGenerator(name = "EMPLOYMENT_SEQ", sequenceName = "EMPLOYMENT_SEQ", initialValue = 1000, allocationSize = 1)
    private Long id;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(optional = false)
    private HaulingCompany hauler;

    @ManyToOne(optional = false)
    private DriverUser driver;

    @Version
    private Integer txLock;

    public Employment() {
        super();
    }

    public Employment(DriverUser driver, HaulingCompany hauler) {
        super();
        this.driver = driver;
        this.hauler = hauler;
        startDate = LocalDate.now();
    }

    public Employment(DriverUser driver, HaulingCompany hauler, LocalDate startDate) {
        super();
        this.driver = driver;
        this.hauler = hauler;
        this.startDate = startDate;
    }

    @AssertTrue(message = "Driver must have at least one Hauler")
    public boolean isEmployed() {

        return driver.getEmployers().size() > 0
                && driver.getEmployers().stream().anyMatch(employer -> employer.getEndDate() == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employment employment = (Employment) o;
        if (employment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hauler: ").append(getHauler()).append(", ");
        sb.append("Driver: ").append(getDriver()).append(", ");
        sb.append("Start Date: ").append(getStartDate()).append(", ");
        sb.append("End Date: ").append(getEndDate()).append(", ");
        return sb.toString();
    }
}
