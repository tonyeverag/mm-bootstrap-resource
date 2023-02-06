package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, defaultImpl=PlantDTO.class)
@AllArgsConstructor
public class PlantDTO extends LocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private CompanyDTO owningCompany;

    private Boolean supportReceivingIntegration;

    private Boolean warnOnUnplannedLoad;

    @NotNull
    private Boolean enableSamples;

    @NotNull
    private Boolean requireSamples;

    @NotNull
    private Boolean noScale;

    private Boolean rejectsUnplannedLoads;

    private Boolean scaleImageRequiredOnDelivery;

    @NotNull
    public Boolean getAllowsDrops() {
        return super.getAllowsDrops();
    }

    @Override
    public String toString() {
        return "PlantDTO{" + "id=" + getId() + ", name='" + getName() + "'" + '}';
    }
}
