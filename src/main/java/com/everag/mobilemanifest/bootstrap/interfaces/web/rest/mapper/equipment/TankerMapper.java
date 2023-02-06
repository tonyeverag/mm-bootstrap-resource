package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tanker;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.CompanyMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "springlazy", uses = { CompanyMapper.class, BarcodeMapper.class })
public interface TankerMapper {
    TankerDTO tankerToTankerDTO(Tanker tanker);

    @Bootstrap
    @Mapping(target = "ownedBy", qualifiedBy = ReferenceOnly.class)
    @Mapping(target = "barcodes", qualifiedBy = ReferenceOnly.class)
    TankerDTO tankerToTankerBootstrapDTO(Tanker tanker);


    List<TankerDTO> tankersToTankerDTOs(List<Tanker> tankers);

    @Mapping(target = "status", defaultValue = "NEW")
    Tanker tankerDTOToTanker(TankerDTO tankerDTO);


}
