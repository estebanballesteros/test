package com.octagon.costooperacion.service;

import com.octagon.costooperacion.domain.PricingHistory;

import com.octagon.costooperacion.service.dto.PricingHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PricingHistory.
 */
public interface PricingHistoryService {

    /**
     * Save a pricingHistory.
     *
     * @param pricingHistory the entity to save
     * @return the persisted entity
     */
    PricingHistory save(PricingHistory pricingHistory);

    /**
     * Get all the pricingHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PricingHistory> findAll(Pageable pageable);


    /**
     * Get the "id" pricingHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PricingHistoryDTO findOne(Long id);

    /**
     * Delete the "id" pricingHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
