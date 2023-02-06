package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.SealRemovalReasonStatus;
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
public class SealRemovalReasonDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    public SealRemovalReasonDTO(){}

    /*Mandatory args constructor*/
    public SealRemovalReasonDTO(String description){
        //used by unit tests
        this.description= description;
        this.setStatus(SealRemovalReasonStatus.ACTIVE);
    }

    private Long id;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private SealRemovalReasonStatus status;

    private Integer txLock;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SealRemovalReasonDTO sealRemovalReasonDTO = (SealRemovalReasonDTO) o;

        if ( ! Objects.equals(getId(), sealRemovalReasonDTO.getId())) return false;

        return true;
    }

    @Override
    public String toString() {
        return "SealDTO{" +
                "id=" + getId() +
                ", description='" + description + "'" +
                ", status='" + status.getDisplayName() + "'" +
                '}';
    }
}
