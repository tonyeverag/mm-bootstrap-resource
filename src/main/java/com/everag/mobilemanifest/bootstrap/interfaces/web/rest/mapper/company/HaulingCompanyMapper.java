package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.HaulingCompany;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.HaulingCompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.NoCollections;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.user.EmploymentMapper;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "springLazy", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {CompanyMapper.class, EmploymentMapper.class})
public interface HaulingCompanyMapper {

    @NoCollections
    HaulingCompanyDTO haulingCompanyToHaulingCompanyDTONoCollections(HaulingCompany haulingCompany);


    @ReferenceOnly
    default HaulingCompanyDTO haulingCompanyToHaulingCompanyDTOReference(HaulingCompany haulingCompany) {
        if (haulingCompany == null) {
            return null;
        }
        HaulingCompanyDTO haulingCompanyDTO = new HaulingCompanyDTO();
        haulingCompanyDTO.setId(haulingCompany.getId());
        haulingCompanyDTO.setTxLock(haulingCompany.getTxLock());
        return haulingCompanyDTO;
    }

    @ReferenceOnly
    default Set<HaulingCompanyDTO> haulingCompaniesToHaulingCompaniesDTOReference(
            Set<HaulingCompany> haulingCompanies) {
        if (haulingCompanies == null) {
            return null;
        }
        return haulingCompanies.stream()
                .map(haulingCompany -> {
                    HaulingCompanyDTO haulingCompanyDTO = new HaulingCompanyDTO();
                    haulingCompanyDTO.setId(haulingCompany.getId());
                    haulingCompanyDTO.setTxLock(haulingCompany.getTxLock());
                    return haulingCompanyDTO;
                })
                .collect(Collectors.toSet());
    }



}
