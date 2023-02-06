package com.everag.mobilemanifest.bootstrap.domain.repository.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealLocation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SealLocationRepository  extends JpaRepository<SealLocation, Long> {

    @Cacheable(value="SEALLOCATIONS", key="#root.method.name")
    List<SealLocation> findAll();

    @Query("select distinct sl from SealLocation sl where lastUpdatedDateTime between :startDate and :endDate")
    List<SealLocation> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select distinct sl from SealLocation sl where lastUpdatedDateTime > :changeDate ")
    List<SealLocation> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);

}