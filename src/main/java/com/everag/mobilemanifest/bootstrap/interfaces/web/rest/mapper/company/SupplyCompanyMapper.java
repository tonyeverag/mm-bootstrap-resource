package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.SupplyCompany;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.SupplierManifestConfigMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplyCompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.FromReference;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "springLazy", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, uses = {
        CompanyMapper.class,  DivisionMapper.class,
        SupplierManifestConfigMapper.class })
public interface SupplyCompanyMapper {

    @Mapping(target = "companyName", source = "company.name")
    SupplyCompanyDTO supplyCompanyToSupplyCompanyDTO(SupplyCompany supplyCompany);

    @Bootstrap
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "divisions", ignore = true)
    SupplyCompanyDTO supplyCompanyToSupplyCompanyBootstrapDTO(SupplyCompany supplyCompany);

    @ReferenceOnly
    default SupplyCompanyDTO supplyCompanyToSupplyCompanyDTOReference(SupplyCompany supplyCompany) {
        if (supplyCompany == null) {
            return null;
        }
        SupplyCompanyDTO dto = new SupplyCompanyDTO();
        dto.setId(supplyCompany.getId());
        dto.setName(supplyCompany.getName());
        dto.setEnableSampleValidation(supplyCompany.getEnableSampleValidation());
        dto.setMaxSampleNumberSize(supplyCompany.getMaxSampleNumberSize());
        dto.setMinSampleNumberSize(supplyCompany.getMinSampleNumberSize());
        dto.setTxLock(supplyCompany.getTxLock());
        return dto;
    }


}
