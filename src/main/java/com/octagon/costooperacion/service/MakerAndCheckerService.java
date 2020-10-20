package com.octagon.costooperacion.service;

import com.octagon.costooperacion.domain.MakerAndChecker;
import com.octagon.costooperacion.service.dto.MakerAndCheckerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MakerAndChecker.
 */
public interface MakerAndCheckerService {

    /**
     * Save a makerAndChecker.
     *
     * @param makerAndCheckerDTO the entity to save
     * @return the persisted entity
     */
    MakerAndCheckerDTO save(MakerAndCheckerDTO makerAndCheckerDTO);

    /**
     * Get all the makerAndCheckers.
     *
     * @return the list of entities
     */
    List<MakerAndCheckerDTO> findAll();


    /**
     * Get the "id" makerAndChecker.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MakerAndCheckerDTO> findOne(Long id);

    /**
     * Delete the "id" makerAndChecker.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
