package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.everag.mobilemanifest.bootstrap.application.PlantService;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.PlantRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.PlantDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location.PlantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    public PlantServiceImpl(PlantRepository plantRepository, PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }



    @Override
    @Transactional(readOnly = true)
    public List<PlantDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return plantRepository.findAll(EntityGraphUtils.fromAttributePaths("owningCompany")).stream()
                .filter(p -> p.getLastUpdatedDateTime() != null && date.isBefore(p.getLastUpdatedDateTime()))
                .map(plantMapper::plantToPlantBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return plantRepository.findAll(EntityGraphUtils.fromAttributePaths("owningCompany")).stream()
                .filter(p -> p.getLastUpdatedDateTime() != null && startTime.isBefore(p.getLastUpdatedDateTime()) && endTime.isAfter(p.getLastUpdatedDateTime()))
                .map(plantMapper::plantToPlantBootstrapDTO)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional(readOnly = true)
    public List<PlantDTO> findAllActive() {
        return StreamSupport.stream(
                        plantRepository.findAllByStatus(
                                EntityGraphUtils.fromAttributePaths("owningCompany"), Status.ACTIVE).spliterator(), false)
                .map(plantMapper::plantToPlantDTO)
                .collect(Collectors.toList());
    }


}
