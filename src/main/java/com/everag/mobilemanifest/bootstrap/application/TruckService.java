package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TruckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TruckService {

    List<TruckDTO> findAll();

    List<TruckDTO> findByLastUpdatedDateTimeAfter(LocalDateTime date);

    List<TruckDTO> findByLastUpdatedDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    Page<TruckDTO> findAll(Pageable pageable);

}
