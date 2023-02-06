package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.AuditedEntity;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Audited
@Getter
@Setter
public abstract class Location  extends AuditedEntity implements Serializable {
    public static final int LAT_DIGITS_INTEGER = 2;
    public static final int LAT_DIGITS_FRACTION = 16;
    public static final String LAT_DECIMAL_MIN = "-90";
    public static final String LAT_DECIMAL_MAX = "90";
    public static final String LAT_REGEXP = "^-?([1-8]?\\d(\\.\\d{0,8})?|90(\\.0{0,8})?)$";
    public static final int LON_DIGITS_INTEGER = 3;
    public static final int LON_DIGITS_FRACTION = 16;
    public static final String LON_DECIMAL_MIN = "-180";
    public static final String LON_DECIMAL_MAX = "180";
    public static final String LON_REGEXP = "^-?(180(\\.0{0,8})?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d{0,8})?)$";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_SEQ")
    @SequenceGenerator(name = "LOCATION_SEQ", sequenceName = "LOCATION_SEQ", initialValue = 1000, allocationSize = 1)
    private Long id;

    @NotNull
    private Boolean allowsDrops;

    @Min(0)
    private Integer geofenceRadius; // in meters

    @NotNull
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 3)
    private ZoneId timezone;

    @Version
    private Integer txLock;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private PostalAddress address;

    @NotNull
    private Status status;

    public Location() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Location(String name, PostalAddress address, Boolean allowsDrops, Status status) {
        super();
        this.name = name;
        this.address = address;
        this.allowsDrops = allowsDrops;
        this.status = status;
    }

    public abstract BigDecimal getLatitude();
    public abstract void setLatitude(BigDecimal latitude);
    public abstract BigDecimal getLongitude();
    public abstract void setLongitude(BigDecimal longitude);

    public Boolean isAllowsDrops() {
        return allowsDrops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name='" + name + "'" + ", allowsDrops='" + allowsDrops + "'" + '}';
    }
}
