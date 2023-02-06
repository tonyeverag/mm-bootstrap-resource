package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.everag.mobilemanifest.bootstrap.application.DropYardService;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.DropYardRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.DropYardDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location.DropYardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("dropYardService")
@Transactional
public class DropYardServiceImpl implements DropYardService {
    private final DropYardRepository dropYardRepository;
    private final DropYardMapper dropYardMapper;

    public DropYardServiceImpl(DropYardRepository dropYardRepository, DropYardMapper dropYardMapper) {
        this.dropYardRepository = dropYardRepository;
        this.dropYardMapper = dropYardMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DropYardDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return dropYardRepository.findAll(EntityGraphUtils.fromAttributePaths("owningCompany")).stream()
                .filter(d -> d.getLastUpdatedDateTime() != null && date.isBefore(d.getLastUpdatedDateTime()))
                .map(dropYardMapper::dropYardToDropYardBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DropYardDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return dropYardRepository.findAll(EntityGraphUtils.fromAttributePaths("owningCompany")).stream()
                .filter(d -> d.getLastUpdatedDateTime() != null && startTime.isBefore(d.getLastUpdatedDateTime()) && endTime.isAfter(d.getLastUpdatedDateTime()))
                .map(dropYardMapper::dropYardToDropYardBootstrapDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DropYardDTO> findAllActive() {
        return StreamSupport.stream(
                        dropYardRepository.findAllByStatus(
                                EntityGraphUtils.fromAttributePaths("owningCompany"), Status.ACTIVE).spliterator(), false)
                .map(dropYardMapper::dropYardToDropYardDTO)
                .collect(Collectors.toList());
    }

}
