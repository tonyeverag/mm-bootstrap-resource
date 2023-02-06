package com.everag.mobilemanifest.bootstrap.domain.repository.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SamplePurpose;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SamplePurposeRepository extends JpaRepository<SamplePurpose, Long> {

    @Cacheable(value="SAMPLEPURPOSES", key="#root.method.name")
    List<SamplePurpose> findAll();


    @Query("select distinct sp from SamplePurpose sp where lastUpdatedDateTime between :startDate and :endDate")
    List<SamplePurpose> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select distinct sp from SamplePurpose sp where lastUpdatedDateTime > :changeDate ")
    List<SamplePurpose> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);

}
