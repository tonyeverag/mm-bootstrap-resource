package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class DriverUserDTO extends TransporterUserDTO {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1)
    private Set<EmploymentDTO> employers = new HashSet<>();


    public String standardAuthority() {
        return "ROLE_DRIVER";
    }

    @AssertTrue(message = "Driver must have at least one Hauler")
    public boolean isEmployed() {
        return !employers.isEmpty() && employers.stream().anyMatch(employer -> employer.getEndDate() == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverUserDTO driverUserDTO = (DriverUserDTO) o;
        return Objects.equals(getId(), driverUserDTO.getId());
    }
}
