package com.octagon.costooperacion.service.impl;

import com.octagon.costooperacion.domain.DollarExchange;
import com.octagon.costooperacion.repository.DollarExchangeRepository;
import com.octagon.costooperacion.service.DollarExchangeService;
import com.octagon.costooperacion.service.dto.DollarExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class DollarExchangeServiceImpl implements DollarExchangeService {

    private final Logger log = LoggerFactory.getLogger(DollarExchangeServiceImpl.class);

    @Autowired
    private DollarExchangeRepository dollarExchangeRepository;

    /**
     * Get all the DollarExchange
     * @param pageable
     * @return
     */
    @Override
    public Page<DollarExchange> findAll(Pageable pageable) {
        return dollarExchangeRepository.findAll(pageable);
    }

    /**
     * Save a dollar exchange
     * @param dollarExchangeDTO
     * @return
     */
    @Override
    public DollarExchangeDTO save(DollarExchangeDTO dollarExchangeDTO) {
        log.debug("Request to save Pricing : {}", dollarExchangeDTO);
        DollarExchange dollarExchange = new DollarExchange(dollarExchangeDTO.getMonto());
        dollarExchange.setFecha(Instant.now());
        dollarExchangeRepository.save(dollarExchange);
        return dollarExchangeDTO;
    }

    /**
     * get the current dollar exchange
     * @return
     */
    @Override
    public DollarExchangeDTO getCurrentExchange() {
        DollarExchange dollarExchange = dollarExchangeRepository.findFirstOrderByFecha();
        return new DollarExchangeDTO(dollarExchange.getMonto());
    }
}
