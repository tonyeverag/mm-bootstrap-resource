package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.user;

import com.everag.mobilemanifest.bootstrap.domain.model.security.DriverUser;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user.DriverUserDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.FromDriver;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "springLazy", uses = {EmploymentMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface DriverUserMapper {

    @Mapping(target = "employers", qualifiedBy = FromDriver.class)
    DriverUserDTO driverUserToDriverUserDTO(DriverUser driverUser);

}
