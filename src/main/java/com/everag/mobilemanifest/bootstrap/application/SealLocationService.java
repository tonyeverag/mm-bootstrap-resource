package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealLocationDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SealLocationService {

    List<SealLocationDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<SealLocationDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<SealLocationDTO> findAll();

}
