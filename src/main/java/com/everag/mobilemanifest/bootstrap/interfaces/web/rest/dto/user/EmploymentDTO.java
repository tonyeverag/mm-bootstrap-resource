package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.HaulingCompanyDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = EmploymentDTO.class)
public class EmploymentDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate endDate;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private HaulingCompanyDTO hauler;

    @NotNull
    private DriverUserDTO driver;

    private Integer txLock;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmploymentDTO employmentDTO = (EmploymentDTO) o;

        if (!Objects.equals(id, employmentDTO.id))
            return false;

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
        return "EmploymentDTO{" + "id=" + id + ", endDate='" + endDate + "'" + ", startDate='" + startDate + "'" + '}';
    }
}
