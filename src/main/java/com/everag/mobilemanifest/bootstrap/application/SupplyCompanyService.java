package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplyCompanyDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SupplyCompanyService {
    List<SupplyCompanyDTO> findAll();

    List<SupplyCompanyDTO> findWithUIConfigurationsByIdNotNull();

    List<SupplyCompanyDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<SupplyCompanyDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
