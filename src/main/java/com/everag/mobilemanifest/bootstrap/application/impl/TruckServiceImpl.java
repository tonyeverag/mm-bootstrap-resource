package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.everag.mobilemanifest.bootstrap.application.TruckService;
import com.everag.mobilemanifest.bootstrap.domain.repository.equipment.TruckRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TruckDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment.TruckMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TruckServiceImpl implements TruckService {
    private final TruckRepository truckRepository;

    private final TruckMapper truckMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TruckDTO> findAll() {
        return StreamSupport.stream(
                        truckRepository.findAll(
                                EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).spliterator(), false)
                .map(truckMapper::truckToTruckBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TruckDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return truckRepository.findAll(EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).stream()
                .filter(t -> t.getLastUpdatedDateTime() != null && date.isBefore(t.getLastUpdatedDateTime()))
                .map(truckMapper::truckToTruckBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TruckDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return truckRepository.findAll(EntityGraphUtils.fromAttributePaths("barcodes", "ownedBy")).stream()
                .filter(t -> t.getLastUpdatedDateTime() != null && startTime.isBefore(t.getLastUpdatedDateTime()) && endTime.isAfter(t.getLastUpdatedDateTime()))
                .map(truckMapper::truckToTruckBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TruckDTO> findAll(Pageable pageable) {
        return truckRepository.findAll(pageable)
                .map(truckMapper::truckToTruckDTO);
    }


}
