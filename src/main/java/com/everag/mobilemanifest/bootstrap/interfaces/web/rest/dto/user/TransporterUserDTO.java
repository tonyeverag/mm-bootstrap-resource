package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class TransporterUserDTO extends SysUserDTO {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransporterUserDTO transporterUserDTO = (TransporterUserDTO) o;

        return Objects.equals(getId(), transporterUserDTO.getId());
    }
}
