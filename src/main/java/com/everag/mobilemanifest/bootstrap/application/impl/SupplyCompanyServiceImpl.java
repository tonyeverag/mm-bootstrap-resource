package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.everag.mobilemanifest.bootstrap.application.SupplyCompanyService;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.SupplyCompanyRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplyCompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.SupplyCompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyCompanyServiceImpl implements SupplyCompanyService {
    private final SupplyCompanyRepository supplyCompanyRepository;
    private final SupplyCompanyMapper supplyCompanyMapper;


    @Override
    @Transactional(readOnly = true)
    public List<SupplyCompanyDTO> findAll() {
        return StreamSupport.stream(
                        supplyCompanyRepository.findAll(
                                EntityGraphUtils.fromAttributePaths("company")).spliterator(), false)
                .map(supplyCompanyMapper::supplyCompanyToSupplyCompanyBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyCompanyDTO> findWithUIConfigurationsByIdNotNull() {
        return supplyCompanyRepository.findByIdNotNull(EntityGraphs.named("SupplyCompany.uiConfigurations")).stream()
                .map(supplyCompanyMapper::supplyCompanyToSupplyCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyCompanyDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return supplyCompanyRepository.findAll(EntityGraphUtils.fromAttributePaths("company")).stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && date.isBefore(s.getLastUpdatedDateTime()))
                .map(supplyCompanyMapper::supplyCompanyToSupplyCompanyBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyCompanyDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return supplyCompanyRepository.findAll(EntityGraphUtils.fromAttributePaths("company")).stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && startTime.isBefore(s.getLastUpdatedDateTime()) && endTime.isAfter(s.getLastUpdatedDateTime()))
                .map(supplyCompanyMapper::supplyCompanyToSupplyCompanyBootstrapDTO)
                .collect(Collectors.toList());
    }


}
