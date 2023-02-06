package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyService {

    List<CompanyDTO> findAll();

    Page<CompanyDTO> findAll(Pageable pageable);

    List<CompanyDTO> findAllCompaniesBootstrap();

    List<CompanyDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<CompanyDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
