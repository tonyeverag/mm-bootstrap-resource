package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class BtuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max=255)
    private String name;

    @NotNull
    @Size(max=255)
    private String btuNumber;

    private Integer txLock;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BtuDTO btuDTO = (BtuDTO) o;

        if (!Objects.equals(id, btuDTO.id))
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
        return "BTUDTO{" + "id=" + id + ", btuNumber='" + btuNumber + "'" + ", name='" + name + "'" + '}';
    }
}
