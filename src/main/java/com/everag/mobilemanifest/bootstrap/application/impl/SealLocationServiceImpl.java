package com.everag.mobilemanifest.bootstrap.application.impl;

import com.everag.mobilemanifest.bootstrap.application.SealLocationService;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SealLocationRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealLocationDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration.SealLocationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SealLocationServiceImpl implements SealLocationService {
    private final SealLocationRepository sealLocationRepository;
    private final SealLocationMapper sealLocationMapper;

    public SealLocationServiceImpl(SealLocationRepository sealLocationRepository, SealLocationMapper sealLocationMapper) {
        this.sealLocationRepository = sealLocationRepository;
        this.sealLocationMapper = sealLocationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealLocationDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return sealLocationRepository.findAll().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && date.isBefore(s.getLastUpdatedDateTime()))
                .map(sealLocationMapper::sealLocationToSealLocationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealLocationDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return sealLocationRepository.findAll().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && startTime.isBefore(s.getLastUpdatedDateTime()) && endTime.isAfter(s.getLastUpdatedDateTime()))
                .map(sealLocationMapper::sealLocationToSealLocationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealLocationDTO> findAll() {
        return sealLocationRepository.findAll().stream()
                .map(sealLocationMapper::sealLocationToSealLocationDTO)
                .collect(Collectors.toList());
    }

}
