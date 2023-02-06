package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DropYardDTO extends LocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private CompanyDTO owningCompany;

    public Boolean getAllowsDrops() {
        return super.getAllowsDrops();
    }

    @Override
    public String toString() {
        return "DropYardDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                '}';
    }
}
