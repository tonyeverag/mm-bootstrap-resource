package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealLocation;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealLocationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "springLazy")
public interface SealLocationMapper {

    SealLocationDTO sealLocationToSealLocationDTO(SealLocation sealLocation);

    List<SealLocationDTO> sealLocationsToSealLocationDTOs(List<SealLocation> sealLocations);

}
