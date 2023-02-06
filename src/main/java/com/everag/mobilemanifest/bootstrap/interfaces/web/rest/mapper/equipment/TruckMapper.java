package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Truck;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TruckDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.CompanyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "springLazy", uses = { CompanyMapper.class, BarcodeMapper.class })
public interface TruckMapper {
    TruckDTO truckToTruckDTO(Truck truck);

    List<TruckDTO> trucksToTruckDTOs(List<Truck> trucks);

    @Bootstrap
    @Mapping(target = "ownedBy", qualifiedBy = ReferenceOnly.class)
    TruckDTO truckToTruckBootstrapDTO(Truck truck);

}
