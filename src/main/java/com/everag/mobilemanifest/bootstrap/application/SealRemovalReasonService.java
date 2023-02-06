package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealRemovalReasonDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SealRemovalReasonService {

    List<SealRemovalReasonDTO> findActiveByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<SealRemovalReasonDTO> findActiveByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<SealRemovalReasonDTO> findAll();

    List<SealRemovalReasonDTO> findAllActive();

}
