package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Division;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.DivisionDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "springLazy", uses = {SupplyCompanyMapper.class})
public interface DivisionMapper {


    DivisionDTO divisionToDivisionDTO(Division division);

    List<DivisionDTO> divisionsToDivisionDTOs(List<Division> divisions);

}
