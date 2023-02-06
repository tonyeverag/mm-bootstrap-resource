package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealRemovalReason;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealRemovalReasonDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "springLazy")
public interface SealRemovalReasonMapper {
    SealRemovalReasonDTO sealRemovalReasonToSealRemovalReasonDTO(SealRemovalReason removalReason);

    List<SealRemovalReasonDTO> sealRemovalReasonsToSealRemovalReasonDTOs(List<SealRemovalReason> removalReasons);

}

