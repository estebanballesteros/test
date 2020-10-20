package com.octagon.costooperacion.service;

import com.octagon.costooperacion.domain.Costo;
import com.octagon.costooperacion.service.dto.CostoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Costo.
 */
public interface CostoService {

    /**
     * Save a costo.
     *
     * @param costoDTO the entity to save
     * @return the persisted entity
     */
    CostoDTO save(CostoDTO costoDTO);

    /**
     * Get all the costos.
     *
     * @return the list of entities
     */
    List<CostoDTO> findAll();


    /**
     * Get the "id" costo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CostoDTO> findOne(Long id);

    /**
     * Delete the "id" costo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
