package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HaulingCompanyDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private CompanyDTO company;

    @NotNull
    private Boolean supportTruckTracking = false;

    private Integer txLock;

    @NotNull
    private Boolean supportRoutesLinking = false;

    @NotNull
    private Boolean paperManifestEnabled = false;

    @NotNull
    private boolean supportMultiTankPickups = false;

    @NotNull
    private Boolean supportSplitTanker = false;

    @NotNull
    private Boolean supportLongHaulResponsible = false;


    @Size(max = 10)
    private String licenseNumber;

    public String getName() {

        if (company == null) {
            return null;
        }
        return company.getName();
    }

    public boolean isSupportRoutesLinking() {
        return supportRoutesLinking;
    }

    public boolean isSupportMultiTankPickups() {
        return supportMultiTankPickups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HaulingCompanyDTO haulingCompanyDTO = (HaulingCompanyDTO) o;

        if (!Objects.equals(id, haulingCompanyDTO.id))
            return false;

        return true;
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
        return "HaulingCompanyDTO{" + "id=" + id + '}';
    }

}
