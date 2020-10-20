package com.octagon.costooperacion.service.impl;

import com.octagon.costooperacion.service.PricingHistoryService;
import com.octagon.costooperacion.domain.PricingHistory;
import com.octagon.costooperacion.repository.PricingHistoryRepository;
import com.octagon.costooperacion.service.dto.PricingHistoryDTO;
import com.octagon.costooperacion.service.mapper.PricingHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PricingHistory.
 */
@Service
@Transactional
public class PricingHistoryServiceImpl implements PricingHistoryService {

    private final Logger log = LoggerFactory.getLogger(PricingHistoryServiceImpl.class);

    private final PricingHistoryRepository pricingHistoryRepository;

    @Autowired
    private PricingHistoryMapper pricingHistoryMapper;

    public PricingHistoryServiceImpl(PricingHistoryRepository pricingHistoryRepository) {
        this.pricingHistoryRepository = pricingHistoryRepository;
    }

    /**
     * Save a pricingHistory.
     *
     * @param pricingHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public PricingHistory save(PricingHistory pricingHistory) {
        log.debug("Request to save PricingHistory : {}", pricingHistory);
        return pricingHistoryRepository.save(pricingHistory);
    }

    /**
     * Get all the pricingHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PricingHistory> findAll(Pageable pageable) {
        log.debug("Request to get all PricingHistories");
        return pricingHistoryRepository.findAll(pageable);
    }


    /**
     * Get one pricingHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PricingHistoryDTO findOne(Long id) {
        log.debug("Request to get PricingHistory : {}", id);
        return pricingHistoryMapper.toDto(pricingHistoryRepository.findById(id).get());
    }

    /**
     * Delete the pricingHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricingHistory : {}", id);
        pricingHistoryRepository.deleteById(id);
    }
}
