package com.everag.mobilemanifest.bootstrap.domain.repository.equipment;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tanker;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TankerRepository  extends EntityGraphJpaRepository<Tanker, Long>, JpaSpecificationExecutor<Tanker> {
    @Cacheable(value="TANKERS", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<Tanker> findAll(EntityGraph entityGraph);

    @Query("select distinct t from Tanker t join fetch t.barcodes join fetch t.ownedBy where t.lastUpdatedDateTime between :startDate and :endDate")
    List<Tanker> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select distinct t from Tanker t join fetch t.barcodes join fetch t.ownedBy where t.lastUpdatedDateTime > :changeDate ")
    List<Tanker> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);

}
