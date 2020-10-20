package com.octagon.costooperacion.service.impl;

import com.octagon.costooperacion.service.CostoService;
import com.octagon.costooperacion.domain.Costo;
import com.octagon.costooperacion.repository.CostoRepository;
import com.octagon.costooperacion.service.dto.CostoDTO;
import com.octagon.costooperacion.service.mapper.CostoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Costo.
 */
@Service
@Transactional
public class CostoServiceImpl implements CostoService {

    private final Logger log = LoggerFactory.getLogger(CostoServiceImpl.class);

    private final CostoRepository costoRepository;

    public CostoServiceImpl(CostoRepository costoRepository) {
        this.costoRepository = costoRepository;
    }

    @Autowired
    public CostoMapper costoMapper;
    /**
     * Save a costo.
     *
     * @param costo the entity to save
     * @return the persisted entity
     */
    @Override
    public CostoDTO save(CostoDTO costoDTO) {
        log.debug("Request to save Costo : {}", costoDTO);
        return costoMapper.toDto(costoRepository.save(costoMapper.toEntity(costoDTO)));
    }

    /**
     * Get all the costos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CostoDTO> findAll() {
        log.debug("Request to get all Costos");
        return costoRepository.findAll().stream().map(e -> costoMapper.toDto(e)).collect(Collectors.toList());
    }


    /**
     * Get one costo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CostoDTO> findOne(Long id) {
        log.debug("Request to get Costo : {}", id);
        return Optional.of(costoMapper.toDto(costoRepository.findById(id).get()));
    }

    /**
     * Delete the costo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Costo : {}", id);
        costoRepository.deleteById(id);
    }
}
