package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.everag.mobilemanifest.bootstrap.application.TankerService;
import com.everag.mobilemanifest.bootstrap.domain.repository.equipment.TankerRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment.TankerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TankerServiceImpl implements TankerService {

    private final TankerRepository tankerRepository;
    private final TankerMapper tankerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TankerDTO> findAll() {
        return StreamSupport.stream(
                        tankerRepository.findAll(
                                EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).spliterator(), false)
                .map(tankerMapper::tankerToTankerBootstrapDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Page<TankerDTO> findAll(Pageable pageable) {
        return tankerRepository.findAll(pageable)
                .map(tankerMapper::tankerToTankerDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TankerDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return tankerRepository.findAll(EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).stream()
                .filter(t -> t.getLastUpdatedDateTime() != null && date.isBefore(t.getLastUpdatedDateTime()))
                .map(tankerMapper::tankerToTankerBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TankerDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return tankerRepository.findAll(EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).stream()
                .filter(t -> t.getLastUpdatedDateTime() != null && startTime.isBefore(t.getLastUpdatedDateTime()) && endTime.isAfter(t.getLastUpdatedDateTime()))
                .map(tankerMapper::tankerToTankerBootstrapDTO)
                .collect(Collectors.toList());
    }

}
