package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class DivisionDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @JsonBackReference("supplyCompanyDivisions")
    private SupplyCompanyDTO supplyCompany;

    private Integer txLock;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DivisionDTO divisionDTO = (DivisionDTO) o;

        if ( ! Objects.equals(id, divisionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        }
        else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return "DivisionDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
