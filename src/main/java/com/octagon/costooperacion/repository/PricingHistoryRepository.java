package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.PricingHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PricingHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingHistoryRepository extends JpaRepository<PricingHistory, Long> {

}
