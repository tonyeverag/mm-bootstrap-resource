package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, defaultImpl=SupplierManifestConfigDTO.class)
public class SupplierManifestConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer txLock;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer currentVal;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer max;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer min;

    @Size(min = 0, max=1, message="Pad Character must be a single character")
    private String padCharacter;

    @Min(value=0)
    @Max(value=2147483647)
    private Integer length;
}
