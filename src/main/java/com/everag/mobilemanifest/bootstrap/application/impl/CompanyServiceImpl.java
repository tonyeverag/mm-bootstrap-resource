package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.everag.mobilemanifest.bootstrap.application.CompanyService;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.CompanyRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("companyService")
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl  implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findAll() {
        return companyRepository.findAll(EntityGraphs.named("Company.trucksAndTankers")).stream()
                .map(companyMapper::companyToCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable, EntityGraphs.named("Company.trucksAndTankers"))
                .map(companyMapper::companyToCompanyDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return companyRepository.findAll(EntityGraphs.named("Company.trucksAndTankers")).stream()
                .filter(c -> c.getLastUpdatedDateTime() != null && c.getLastUpdatedDateTime() != null && date.isBefore(c.getLastUpdatedDateTime()))
                .map(companyMapper::companyToCompanyDTORef)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return companyRepository.findAll(EntityGraphs.named("Company.trucksAndTankers")).stream()
                .filter(c -> c.getLastUpdatedDateTime() != null && c.getLastUpdatedDateTime() != null && startTime.isBefore(c.getLastUpdatedDateTime()) && endTime.isAfter(c.getLastUpdatedDateTime()))
                .map(companyMapper::companyToCompanyDTORef)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> findAllCompaniesBootstrap() {
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(companyMapper::companyToCompanyDTORef)
                .collect(Collectors.toList());
    }





}
