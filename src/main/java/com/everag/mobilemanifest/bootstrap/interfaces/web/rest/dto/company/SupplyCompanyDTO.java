package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, defaultImpl=SupplyCompanyDTO.class)
public class SupplyCompanyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer txLock;
    @NotNull
    private CompanyDTO company;

    private String companyName;

    @JsonManagedReference("supplyCompanyDivisions")
    private Set<DivisionDTO> divisions = new HashSet<>();

    private Boolean enableAutogenManifest;

    @NotNull
    private Boolean generateBarcode;

    @NotNull
    private Boolean enableSampleValidation;

    @NotNull
    private Integer maxSampleNumberSize;

    @NotNull
    private Integer minSampleNumberSize;

    @NotNull
    private Boolean enableRequireSamples;

    @NotNull
    private Boolean requireScaleImageOnPickup;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyCompanyDTO supplyCompanyDTO = (SupplyCompanyDTO) o;

        if ( ! Objects.equals(getId(), supplyCompanyDTO.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return Objects.hashCode(getId());
        }
        else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        String companyName = "";
        if(company != null) {
            companyName = company.getName();
        }
        return "SupplyCmpanyDTO{" +
                "id=" + getId()+
                "company=" + companyName +
                '}';
    }

    public static final Comparator<SupplyCompanyDTO> COMPANY_ALPHA_ORDER = new Comparator<SupplyCompanyDTO>()
    {
        public int compare(SupplyCompanyDTO s1, SupplyCompanyDTO s2)
        {
            int compCmp = s1.getCompanyName().toLowerCase().compareTo(s2.getCompanyName().toLowerCase());

            if (compCmp != 0)
            {
                return compCmp;
            }

            return s1.getId().compareTo(s2.getId());
        }
    };
}
