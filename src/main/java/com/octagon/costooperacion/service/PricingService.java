package com.octagon.costooperacion.service;

import com.octagon.costooperacion.service.dto.FiltroParametriaDTO;
import com.octagon.costooperacion.service.dto.PricingDTO;
import com.octagon.costooperacion.service.utills.OctagonBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Pricing.
 */
public interface PricingService {

    /**
     * Save a pricing.
     *
     * @param pricingDTO the entity to save
     * @return the persisted entity
     */
    PricingDTO save(PricingDTO pricingDTO) throws OctagonBusinessException;

    /**
     * Get all the pricings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PricingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pricing.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PricingDTO> findOne(Long id);

    /**
     * Delete the "id" pricing.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws OctagonBusinessException;

    PricingDTO getPricingByParametria(FiltroParametriaDTO filtroParametriaDTO);
}
