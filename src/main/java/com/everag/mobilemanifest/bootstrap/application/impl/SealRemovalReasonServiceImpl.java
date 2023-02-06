package com.everag.mobilemanifest.bootstrap.application.impl;

import com.everag.mobilemanifest.bootstrap.application.SealRemovalReasonService;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SealRemovalReasonRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealRemovalReasonDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration.SealRemovalReasonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SealRemovalReasonServiceImpl  implements SealRemovalReasonService {

    private final SealRemovalReasonRepository sealRemovalReasonRepository;
    private final SealRemovalReasonMapper sealRemovalReasonMapper;

    public SealRemovalReasonServiceImpl(SealRemovalReasonRepository sealRemovalReasonRepository, SealRemovalReasonMapper sealRemovalReasonMapper) {
        this.sealRemovalReasonRepository = sealRemovalReasonRepository;
        this.sealRemovalReasonMapper = sealRemovalReasonMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SealRemovalReasonDTO> findActiveByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return sealRemovalReasonRepository.findAllActive().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && date.isBefore(s.getLastUpdatedDateTime()))
                .map(sealRemovalReasonMapper::sealRemovalReasonToSealRemovalReasonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealRemovalReasonDTO> findActiveByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return sealRemovalReasonRepository.findAllActive().stream()
                .filter(s -> s.getLastUpdatedDateTime() != null && startTime.isBefore(s.getLastUpdatedDateTime()) && endTime.isAfter(s.getLastUpdatedDateTime()))
                .map(sealRemovalReasonMapper::sealRemovalReasonToSealRemovalReasonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealRemovalReasonDTO> findAll() {
        return sealRemovalReasonRepository.findAll().stream()
                .map(sealRemovalReasonMapper::sealRemovalReasonToSealRemovalReasonDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SealRemovalReasonDTO> findAllActive() {
        return sealRemovalReasonRepository.findAllActive().stream()
                .map(sealRemovalReasonMapper::sealRemovalReasonToSealRemovalReasonDTO)
                .collect(Collectors.toList());
    }

}
