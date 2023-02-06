package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SamplePurposeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SamplePurposeService {

    List<SamplePurposeDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<SamplePurposeDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    List<SamplePurposeDTO> findAll();

}
