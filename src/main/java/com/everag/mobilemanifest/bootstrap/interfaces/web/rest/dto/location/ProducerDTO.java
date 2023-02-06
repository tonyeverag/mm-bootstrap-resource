package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.BtuDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.DivisionDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplyCompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, defaultImpl=ProducerDTO.class)
public class ProducerDTO  extends LocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 100)
    private String producerCode;

    @NotNull
    private SupplyCompanyDTO supplyCompany;

    private BtuDTO btu;

    private DivisionDTO division;

    @JsonManagedReference
    private Set<TankDTO> tanks = new HashSet<>();

    @NotNull
    private Boolean requireSamplesOverride;

    @NotNull
    private Boolean offlineLocation;

    @NotNull
    private Boolean allowAsynchronousPickups;

    @NotNull
    public Boolean getAllowsDrops() {
        return super.getAllowsDrops();
    }

    @Override
    public String toString() {
        return "ProducerDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", producerCode='" + producerCode + "'"
                + ", latitude='" + getLatitude() + "'" + ", longitude='" + getLongitude() + "'" + ", allowDrops='"
                + getAllowsDrops() + "'" +
                ", geofenceRadius='" + getGeofenceRadius() + "'" +
                '}';
    }

}
