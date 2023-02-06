package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Plant;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.PlantDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.TimeZoneMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.FromReference;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.CompanyMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "springLazy", uses = {CompanyMapper.class, TimeZoneMapper.class})
public interface PlantMapper {

    PlantDTO plantToPlantDTO(Plant plant);


    @Bootstrap
    @Mapping(target = "owningCompany", qualifiedBy = ReferenceOnly.class)
    PlantDTO plantToPlantBootstrapDTO(Plant plant);


}
