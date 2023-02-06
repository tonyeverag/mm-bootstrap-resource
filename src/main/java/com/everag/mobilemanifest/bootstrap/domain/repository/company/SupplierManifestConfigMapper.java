package com.everag.mobilemanifest.bootstrap.domain.repository.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.SupplierManifestConfig;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplierManifestConfigDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "springLazy", uses = {})
public interface SupplierManifestConfigMapper {

    SupplierManifestConfigDTO supplierManifestConfigToSupplierManifestConfigDTO(SupplierManifestConfig config);

    List<SupplierManifestConfigDTO> supplierManifestConfigsToSupplierManifestConfigDTOs(List<SupplierManifestConfig> configs);

    SupplierManifestConfig supplierManifestConfigDTOToSupplierManifestConfig(SupplierManifestConfigDTO dto);

    List<SupplierManifestConfig> supplierManifestConfigDTOsToSupplierManifestConfigs(List<SupplierManifestConfigDTO> dtos);
}
