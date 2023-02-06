package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.*;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.ValidationGroup;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.ProducerDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TankDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(groups= ValidationGroup.DeviceValidation.class)
    @Size(min = 1, max = 25, message="Tank number must be 1-25 characters long")
    @Pattern(regexp = "^.{1,25}$",groups= ValidationGroup.DeviceValidation.class)
    private String tankNumber;

    @Min(0)
    private BigDecimal calibrationRange;

    @NotNull
    private ReadMethod defaultReadMethod;

    @NotNull(groups= ValidationGroup.DeviceValidation.class)
    private TankWeightUom weightUom;

    @NotNull(groups= ValidationGroup.DeviceValidation.class)
    private StickReadingUom stickReadingUom;

    @JsonBackReference
    private ProducerDTO ownedBy;

    private Integer txLock;

    private Set<BarcodeDTO> barcodes = new HashSet<>();

    @NotNull
    private MilkGrade milkGrade;

    @NotNull
    private MilkType milkType;

    private Boolean enableNoWeightAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TankDTO tankDTO = (TankDTO) o;

        if ( ! Objects.equals(id, tankDTO.id)) return false;

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
        return "TankDTO{" +
                "id=" + id +
                ", tankNumber='" + tankNumber + '\'' +
                ", calibrationRange=" + calibrationRange +
                ", defaultReadMethod=" + defaultReadMethod +
                ", weightUom=" + weightUom +
                ", stickReadingUom=" + stickReadingUom +
                ", ownedBy=" + ownedBy +
                ", txLock=" + txLock +
                ", barcodes=" + barcodes +
                ", milkGrade=" + milkGrade +
                ", milkType=" + milkType +
                '}';
    }

}
