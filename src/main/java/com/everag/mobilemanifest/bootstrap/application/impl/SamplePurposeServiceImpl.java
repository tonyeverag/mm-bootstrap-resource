package com.everag.mobilemanifest.bootstrap.application.impl;

import com.everag.mobilemanifest.bootstrap.application.SamplePurposeService;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SamplePurposeRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SamplePurposeDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration.SamplePurposeMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SamplePurposeServiceImpl implements SamplePurposeService {
    private final SamplePurposeRepository samplePurposeRepository;
    private final SamplePurposeMapper samplePurposeMapper;

    public SamplePurposeServiceImpl(SamplePurposeRepository samplePurposeRepository, SamplePurposeMapper samplePurposeMapper) {
        this.samplePurposeRepository = samplePurposeRepository;
        this.samplePurposeMapper = samplePurposeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SamplePurposeDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return samplePurposeRepository.findAll().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && date.isBefore(s.getLastUpdatedDateTime()))
                .map(samplePurposeMapper::samplePurposeToSamplePurposeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SamplePurposeDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return samplePurposeRepository.findAll().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && startTime.isBefore(s.getLastUpdatedDateTime()) && endTime.isAfter(s.getLastUpdatedDateTime()))
                .map(samplePurposeMapper::samplePurposeToSamplePurposeDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SamplePurposeDTO> findAll() {
        return samplePurposeRepository.findAll().stream()
                .map(samplePurposeMapper::samplePurposeToSamplePurposeDTO)
                .collect(Collectors.toList());
    }

}
