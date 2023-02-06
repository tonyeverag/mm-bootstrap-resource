package com.everag.mobilemanifest.bootstrap.domain.repository.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealRemovalReason;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SealRemovalReasonRepository extends JpaRepository<SealRemovalReason, Long> {
    @Query("select distinct sealRemovalReason from SealRemovalReason sealRemovalReason where lastUpdatedDateTime between :startDate and :endDate")
    List<SealRemovalReason> findByLastUpdatedDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select distinct sealRemovalReason from SealRemovalReason sealRemovalReason where lastUpdatedDateTime > :changeDate ")
    List<SealRemovalReason> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);


    @Query("select distinct sealRemovalReason from SealRemovalReason sealRemovalReason where status = com.dairy.mobilemanifest.domain.model.enums.SealRemovalReasonStatus.ACTIVE ")
    @Cacheable(value="SEALREMOVALREASONS", key="#root.method.name")
    List<SealRemovalReason> findAllActive();

}
