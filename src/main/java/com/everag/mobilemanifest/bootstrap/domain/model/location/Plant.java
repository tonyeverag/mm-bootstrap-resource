package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "owning_company_id", "name" }, name = "UQ_PLANT_NAME") )
@Audited
@Getter
@Setter
@NoArgsConstructor
public class Plant extends Location {

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

    @NotNull
    private Boolean supportReceivingIntegration;

    @NotNull
    private Boolean warnOnUnplannedLoad;

    @NotNull
    private Boolean enableSamples;

    @NotNull
    private Boolean requireSamples;

    @NotNull
    private Boolean noScale;

    @NotNull
    private Boolean rejectsUnplannedLoads;

    @NotNull
    @Column(name="S_IMAGE_REQUIRED_ON_DELIVERY")
    private Boolean scaleImageRequiredOnDelivery;

    /*Mandatory args constructor*/
    public Plant(String name, Company owningCompany, PostalAddress address, Boolean allowsDrops, BigDecimal latitude, BigDecimal longitude, Status status) {
        super(name, address, allowsDrops, status);
        this.longitude = longitude;
        this.latitude = latitude;
        this.owningCompany=owningCompany;
        this.requireSamples = false;
        this.enableSamples = false;
        this.noScale = false;
        this.rejectsUnplannedLoads = false;
        this.scaleImageRequiredOnDelivery = false;
    }

    @PrePersist
    private void beforeSave() {
        if (this.scaleImageRequiredOnDelivery == null){
            this.scaleImageRequiredOnDelivery = false;
        }
        if (this.getAllowsDrops() == null) {
            this.setAllowsDrops(Boolean.FALSE);
        }
        if(supportReceivingIntegration == null) {
            this.setSupportReceivingIntegration(Boolean.FALSE);
        }
        if(warnOnUnplannedLoad == null) {
            this.setWarnOnUnplannedLoad(Boolean.FALSE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plant plant = (Plant) o;
        if (plant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plant.getId());
    }

    @Override
    public String toString() {
        return "Plant [owningCompany=" + owningCompany + ", latitude=" + latitude + ", longitude=" + longitude
                + ", supportReceivingIntegration=" + supportReceivingIntegration + ", warnOnUnplannedLoad="
                + warnOnUnplannedLoad + ", getAddress()=" + getAddress() + ", getAllowsDrops()=" + getAllowsDrops()
                + ", getName()=" + getName() + ", getTimezone()=" + getTimezone() + "]";
    }
}
