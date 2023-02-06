package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SamplePurposeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max=255)
    private String description;

    private Integer txLock;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SamplePurposeDTO samplePurposeDTO = (SamplePurposeDTO) o;

        if ( ! Objects.equals(id, samplePurposeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SamplePurposeDTO{" +
                "id=" + id +
                ", description='" + description + "'" +
                '}';
    }
}
