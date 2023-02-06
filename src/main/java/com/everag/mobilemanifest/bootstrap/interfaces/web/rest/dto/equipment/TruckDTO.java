package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.EntityStatus;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TruckDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private EntityStatus status;

    @NotNull
    @Size(max = 255)
    private String truckId;

    private Integer txLock;

    private CompanyDTO ownedBy;

    private Set<BarcodeDTO> barcodes = new HashSet<>();

    public TruckDTO() {}

    public TruckDTO(String truckNumber) {
        this.truckId = truckNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TruckDTO truckDTO = (TruckDTO) o;

        if (!Objects.equals(id, truckDTO.id))
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
        return "TruckDTO{" + "id=" + id + ", truckId='" + truckId + "'" + '}';
    }
}