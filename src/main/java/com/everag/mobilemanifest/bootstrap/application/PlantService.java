package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.PlantDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PlantService {

    List<PlantDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<PlantDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<PlantDTO> findAllActive();

}
