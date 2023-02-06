package com.everag.mobilemanifest.bootstrap.domain.repository.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.BTU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BTURepository extends JpaRepository<BTU, Long> {
}
