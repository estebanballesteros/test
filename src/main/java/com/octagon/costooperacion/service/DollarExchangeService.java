package com.octagon.costooperacion.service;

import com.octagon.costooperacion.domain.DollarExchange;
import com.octagon.costooperacion.service.dto.DollarExchangeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DollarExchangeService {

    Page<DollarExchange> findAll(Pageable pageable);

    DollarExchangeDTO save(DollarExchangeDTO dollarExchangeDTO);

    DollarExchangeDTO getCurrentExchange();
}
