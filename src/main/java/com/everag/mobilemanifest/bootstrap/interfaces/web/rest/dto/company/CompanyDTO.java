package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Max(value = Long.MAX_VALUE)
    private Long companyNumber;

    private Integer txLock;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;

        if (!Objects.equals(id, companyDTO.id))
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
        return "CompanyDTO{" + "id=" + id + ", name='" + name + "'" + '}';
    }
}