package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "owning_company_id", "name" }, name = "UQ_DROPYARD_NAME") )
@Audited
@Getter
@Setter
public class DropYard extends Location {
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    private Company owningCompany;

    @NotNull
    @Digits(integer = Location.LAT_DIGITS_INTEGER, fraction = Location.LAT_DIGITS_FRACTION)
    @DecimalMin(Location.LAT_DECIMAL_MIN)
    @DecimalMax(Location.LAT_DECIMAL_MAX)
    private BigDecimal latitude;

    @NotNull
    @Digits(integer = Location.LON_DIGITS_INTEGER, fraction = Location.LON_DIGITS_FRACTION)
    @DecimalMin(Location.LON_DECIMAL_MIN)
    @DecimalMax(Location.LON_DECIMAL_MAX)
    private BigDecimal longitude;

    public DropYard() {
        super();
        this.setAllowsDrops(Boolean.TRUE);
    }

    /*
     * Required args constructor
     */
    public DropYard(Company owningCompany, String name, PostalAddress address, BigDecimal latitude, BigDecimal longitude, Status status) {
        super(name, address, Boolean.TRUE, status);
        this.latitude = latitude;
        this.longitude = longitude;
        this.owningCompany = owningCompany;
    }

    @PrePersist
    private void beforeSave() {
        this.setAllowsDrops(Boolean.TRUE);
    }

    @Override
    public String toString() {
        return getName() == null ? " " : getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DropYard dropYard = (DropYard) o;
        if (dropYard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dropYard.getId());
    }
}
