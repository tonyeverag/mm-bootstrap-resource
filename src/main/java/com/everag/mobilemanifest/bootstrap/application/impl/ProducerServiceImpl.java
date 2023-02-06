package com.everag.mobilemanifest.bootstrap.application.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.everag.mobilemanifest.bootstrap.application.ProducerService;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.ProducerRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.user.ProducerUserRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.ProducerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location.ProducerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerUserRepository producerUserRepository;
    private final ProducerMapper producerMapper;



    @Override
    @Transactional(readOnly = true)
    public List<ProducerDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date) {
        return producerRepository.findAll(EntityGraphs.named("producerBootstrap")).stream()
                .filter(p -> p.getLastUpdatedDateTime() != null && date.isBefore(p.getLastUpdatedDateTime()))
                .map(producerMapper::producerToProducerDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProducerDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return producerRepository.findAll(EntityGraphs.named("producerBootstrap")).stream()
                .filter(p -> p.getLastUpdatedDateTime() != null && startTime.isBefore(p.getLastUpdatedDateTime()) && endTime.isAfter(p.getLastUpdatedDateTime()))
                .map(producerMapper::producerToProducerDTO).collect(Collectors.toList());

    }


    @Override
    @Transactional(readOnly = true)
    public List<ProducerDTO> findAllActive() {
        return StreamSupport.stream(
                        producerRepository.findAllByStatus(EntityGraphs.named("producerBootstrap"), Status.ACTIVE).spliterator(), false)
                .map(producerMapper::producerToProducerDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProducerDTO> findAllByProducerUser() {
        var producerUser = producerUserRepository.findCurrentProducerUser();
        if(producerUser.isPresent()) {
            return StreamSupport.stream(
                            producerUser.get().getProducers().spliterator(), false)
                    .map(producerMapper::producerToProducerDTO)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }





}
