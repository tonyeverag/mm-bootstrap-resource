package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SealLocationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 255)
    private String description;

    private Integer txLock;

    public SealLocationDTO() {
        super();
    }

    public SealLocationDTO(String description) {
        super();
        this.description = description;
    }

    public SealLocationDTO(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SealLocationDTO sealLocationDTO = (SealLocationDTO) o;

        if (!Objects.equals(id, sealLocationDTO.id))
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
        return "SealLocationDTO{" + "id=" + id + ", description='" + description + "'" + '}';
    }
}