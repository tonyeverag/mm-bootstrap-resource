package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.user;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Employment;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user.EmploymentDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.FromDriver;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.NoCollections;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.HaulingCompanyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "springLazy", uses = {HaulingCompanyMapper.class, DriverUserMapper.class})
public interface EmploymentMapper {

    @FromDriver
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "hauler", qualifiedBy = NoCollections.class)
    EmploymentDTO employmentToEmploymentDTOFromDriver(Employment employment);

}
