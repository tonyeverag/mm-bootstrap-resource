package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TankerService {

    List<TankerDTO> findAll();

    Page<TankerDTO> findAll(Pageable pageable);

    List<TankerDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<TankerDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

}
