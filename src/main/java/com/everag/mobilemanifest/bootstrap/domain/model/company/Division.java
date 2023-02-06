package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_DIVISION_NAME", columnNames = { "name", "supply_company_id" }) })
@Audited
@Getter
@Setter
public class Division implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIVISION_SEQ")
    @SequenceGenerator(name = "DIVISION_SEQ", sequenceName = "DIVISION_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    @JsonBackReference("supplyCompanyDivisions")
    private SupplyCompany supplyCompany;

    @Version
    private Integer txLock;

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    private Set<Producer> producers = new HashSet<>();

    public Division() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Division(SupplyCompany supplyCompany, String name) {
        this(name);
        this.supplyCompany = supplyCompany;
    }

    public Division(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Division division = (Division) o;
        if (division.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, division.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Division{" + "id=" + id + ", name='" + name + "'" + '}';
    }
}