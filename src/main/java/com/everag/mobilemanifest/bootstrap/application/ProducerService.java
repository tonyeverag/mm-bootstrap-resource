package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.ProducerDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface ProducerService {

    List<ProducerDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<ProducerDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<ProducerDTO> findAllActive();

    List<ProducerDTO> findAllByProducerUser();

}
