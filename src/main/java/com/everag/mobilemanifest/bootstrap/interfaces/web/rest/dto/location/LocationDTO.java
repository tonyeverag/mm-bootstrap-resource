package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Location;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.NumberRegex;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.MobileManifestTypeIdResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "@class", defaultImpl=LocationDTO.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeIdResolver(MobileManifestTypeIdResolver.class)
public class LocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean allowsDrops;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;


    /**
     * Geofence radius in meters
     */
    @Min(0)
    @NotNull
    private Integer geofenceRadius;

    private Integer txLock;

    @NotNull
    private Status status;

    @NotNull
    @Digits(integer= Location.LAT_DIGITS_INTEGER, fraction=Location.LAT_DIGITS_FRACTION, payload = NumberRegex.LATITUDE.class)
    @DecimalMin(Location.LAT_DECIMAL_MIN)
    @DecimalMax(Location.LAT_DECIMAL_MAX)
    private BigDecimal latitude;

    @NotNull
    @Digits(integer=Location.LON_DIGITS_INTEGER, fraction=Location.LON_DIGITS_FRACTION, payload = NumberRegex.LONGITUDE.class)
    @DecimalMin(Location.LON_DECIMAL_MIN)
    @DecimalMax(Location.LON_DECIMAL_MAX)
    private BigDecimal longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        return Objects.equals(id, locationDTO.id);
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
        return "LocationDTO{" + "id=" + id + ", allowsDrops='" + allowsDrops + "'" +
                ", name='" + name + "'" + ", geofenceRadius='" + geofenceRadius	+ "'" + '}';
    }
}
