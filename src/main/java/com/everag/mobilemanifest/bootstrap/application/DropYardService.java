package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.DropYardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DropYardService {

    List<DropYardDTO> findAllActive();

    List<DropYardDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<DropYardDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
