package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location;

import com.everag.mobilemanifest.bootstrap.domain.model.location.DropYard;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.DropYardDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.TimeZoneMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.CompanyMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "springLazy", uses = { CompanyMapper.class, TimeZoneMapper.class })
public interface DropYardMapper {
    DropYardDTO dropYardToDropYardDTO(DropYard dropYard);

    @Bootstrap
    DropYardDTO dropYardToDropYardBootstrapDTO(DropYard dropYard);

}
