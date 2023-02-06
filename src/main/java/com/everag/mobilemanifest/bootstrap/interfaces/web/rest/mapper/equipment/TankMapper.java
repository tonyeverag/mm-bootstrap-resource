package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tank;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {BarcodeMapper.class})
public interface TankMapper {

    @Mapping(target = "ownedBy", ignore = true)
    @Bootstrap
    TankDTO tankToTankDTOBootstrap(Tank tank);
}
