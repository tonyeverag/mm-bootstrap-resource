package com.everag.mobilemanifest.bootstrap.domain.repository.equipment;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Truck;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TruckRepository  extends EntityGraphJpaRepository<Truck, Long>, JpaSpecificationExecutor<Truck> {

    @Cacheable(value="TRUCKS", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<Truck> findAll(EntityGraph entityGraph);


    @Query("select distinct t from Truck t join fetch t.barcodes join fetch t.ownedBy where t.lastUpdatedDateTime between :startDate and :endDate")
    List<Truck> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select distinct t from Truck t join fetch t.barcodes join fetch t.ownedBy where t.lastUpdatedDateTime > :changeDate ")
    List<Truck> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);

}