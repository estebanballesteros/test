package com.octagon.costooperacion.service.impl;

import com.octagon.costooperacion.service.MakerAndCheckerService;
import com.octagon.costooperacion.domain.MakerAndChecker;
import com.octagon.costooperacion.repository.MakerAndCheckerRepository;
import com.octagon.costooperacion.service.dto.MakerAndCheckerDTO;
import com.octagon.costooperacion.service.mapper.MakerAndCheckerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MakerAndChecker.
 */
@Service
@Transactional
public class MakerAndCheckerServiceImpl implements MakerAndCheckerService {

    private final Logger log = LoggerFactory.getLogger(MakerAndCheckerServiceImpl.class);

    private final MakerAndCheckerRepository makerAndCheckerRepository;

    @Autowired
    private MakerAndCheckerMapper makerAndCheckerMapper;

    public MakerAndCheckerServiceImpl(MakerAndCheckerRepository makerAndCheckerRepository) {
        this.makerAndCheckerRepository = makerAndCheckerRepository;
    }

    /**
     * Save a makerAndChecker.
     *
     * @param makerAndCheckerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MakerAndCheckerDTO save(MakerAndCheckerDTO makerAndCheckerDTO) {
        log.debug("Request to save MakerAndChecker : {}", makerAndCheckerDTO);
        return makerAndCheckerMapper.toDto(makerAndCheckerRepository.save(makerAndCheckerMapper.toEntity(makerAndCheckerDTO)));
    }

    /**
     * Get all the makerAndCheckers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MakerAndCheckerDTO> findAll() {
        log.debug("Request to get all MakerAndCheckers");
        return makerAndCheckerRepository.findAll().stream().map(makerAndCheckerMapper::toDto).collect(Collectors.toList());
    }


    /**
     * Get one makerAndChecker by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MakerAndCheckerDTO> findOne(Long id) {
        log.debug("Request to get MakerAndChecker : {}", id);
        return Optional.of(makerAndCheckerMapper.toDto(makerAndCheckerRepository.findById(id).get()));
    }

    /**
     * Delete the makerAndChecker by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MakerAndChecker : {}", id);
        makerAndCheckerRepository.deleteById(id);
    }
}
