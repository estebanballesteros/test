package com.octagon.costooperacion.service.impl;

import com.octagon.costooperacion.domain.Parametria;
import com.octagon.costooperacion.domain.PricingHistory;
import com.octagon.costooperacion.repository.ParametriaRepository;
import com.octagon.costooperacion.service.DollarExchangeService;
import com.octagon.costooperacion.service.PricingHistoryService;
import com.octagon.costooperacion.service.PricingService;
import com.octagon.costooperacion.domain.Pricing;
import com.octagon.costooperacion.repository.PricingRepository;
import com.octagon.costooperacion.service.dto.FiltroParametriaDTO;
import com.octagon.costooperacion.service.dto.PricingDTO;
import com.octagon.costooperacion.service.mapper.PricingMapper;
import com.octagon.costooperacion.service.utills.Constants;
import com.octagon.costooperacion.service.utills.OctagonBusinessException;
import com.octagon.costooperacion.service.utills.ResponseStatus;
import com.octagon.costooperacion.web.rest.errors.BadRequestAlertException;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Pricing.
 */
@Service
@Transactional
public class PricingServiceImpl implements PricingService {

    private final Logger log = LoggerFactory.getLogger(PricingServiceImpl.class);

    private final PricingRepository pricingRepository;

    @Autowired
    private PricingMapper pricingMapper;

    @Autowired
    private ParametriaRepository parametriaRepository;

    @Autowired
    private PricingHistoryService pricingHistoryService;

    @Autowired
    private DollarExchangeService dollarExchangeService;

    public PricingServiceImpl(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    /**
     * Save a pricing.
     *
     * @param pricing the entity to save
     * @return the persisted entity
     */
    @Override
    public PricingDTO save(PricingDTO pricingDTO) throws OctagonBusinessException {
        log.debug("Request to save Pricing : {}", pricingDTO);
        Pricing pricing = pricingMapper.toEntity(pricingDTO);
        setParametria(pricing, pricingDTO);

        /* Se valida que no se intente guardar un pricing con los mismos parametros que uno existente */
        Optional<Pricing> optionalPricing = Optional.of(pricingRepository.findByCanalOperacionAndMonedaAndTipoClienteDestinatarioAndTipoClienteOriginanteAndTipoTransaccionAndFormaPago(
            pricing.getCanalOperacion(),
            pricing.getMoneda(),
            pricing.getTipoClienteDestinatario(),
            pricing.getTipoClienteOriginante(),
            pricing.getTipoTransaccion(),
            pricing.getFormaPago()
        ));
        if (optionalPricing.isPresent()) {
            throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.PRICING_EXISTS.getMessage(), ResponseStatus.PRICING_EXISTS.getMessage());
        }

        pricingDTO = pricingMapper.toDto(pricingRepository.save(pricing));

        PricingHistory pricingHistory = new PricingHistory();
        pricingHistory.setStartDate(Instant.now());
        pricingHistory.setPricing(pricing);
        pricingHistoryService.save(pricingHistory);

        return pricingDTO;
    }

    /**
     * Get all the pricings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PricingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pricings");
        return pricingRepository.findAll(pageable).map(pricingMapper::toDto);
    }


    /**
     * Get one pricing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PricingDTO> findOne(Long id) {
        log.debug("Request to get Pricing : {}", id);
        return Optional.of(pricingMapper.toDto(pricingRepository.findById(id).get()));
    }

    /**
     * Delete the pricing by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws OctagonBusinessException {
        log.debug("Request to delete Pricing : {}", id);
        try {
            pricingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OctagonBusinessException(ResponseStatus.BAD_REQUEST);
        }
    }

    @Override
    public PricingDTO getPricingByParametria(FiltroParametriaDTO filtroParametriaDTO) {
        Pricing pricing = new Pricing();
        PricingDTO pricingDTO = new PricingDTO();
        pricingDTO.setCanalDescCorta(filtroParametriaDTO.getCanalOperacionDescCorta());
        pricingDTO.setFormaPagoDescCorta(filtroParametriaDTO.getFormaPagoDescCorta());
        pricingDTO.setMonedaDescCorta(filtroParametriaDTO.getMonedaDescCorta());
        pricingDTO.setTipoClienteDestinatarioDescCorta(filtroParametriaDTO.getTipoClienteDestinatarioDescCorta());
        pricingDTO.setTipoClienteOriginanteDescCorta(filtroParametriaDTO.getTipoClienteOriginanteDescCorta());
        pricingDTO.setTipoTransaccionDescCorta(filtroParametriaDTO.getTipoTransaccionDescCorta());
        setParametria(pricing, pricingDTO);

        pricing = pricingRepository.findByCanalOperacionAndMonedaAndTipoClienteDestinatarioAndTipoClienteOriginanteAndTipoTransaccionAndFormaPago(
            pricing.getCanalOperacion(),
            pricing.getMoneda(),
            pricing.getTipoClienteDestinatario(),
            pricing.getTipoClienteOriginante(),
            pricing.getTipoTransaccion(),
            pricing.getFormaPago()
        );
        pricingDTO =  pricingMapper.toDto(pricing);
        BigDecimal dollarExchange = dollarExchangeService.getCurrentExchange().getMonto();
        if (pricingDTO.getMontoDolares() != null && dollarExchange != null)
            pricingDTO.setMontoPesosARS(pricingDTO.getMontoDolares().multiply(dollarExchange));
        return pricingDTO;
    }

    /**
     * Set Parametria to pricing
     *
     * @param pricing
     * @param pricingDTO
     */
    private void setParametria(Pricing pricing, PricingDTO pricingDTO){
        if (pricingDTO.getTipoTransaccionDescCorta() != null) {
            Parametria parametriaTipoTransaccion = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_TIPO_TRANSACCION, pricingDTO.getTipoTransaccionDescCorta());
            pricing.setTipoTransaccion(parametriaTipoTransaccion);
        }

        if (pricingDTO.getCanalDescCorta() != null) {
            Parametria parametriaCanal = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_CANAL_OPERACION,pricingDTO.getCanalDescCorta());
            pricing.setCanalOperacion(parametriaCanal);
        }

        if (pricingDTO.getFormaPagoDescCorta() != null) {
            Parametria parametriaFormaDePago = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_FORMA_PAGO, pricingDTO.getFormaPagoDescCorta());
            pricing.setFormaPago(parametriaFormaDePago);
        }

        if (pricingDTO.getTipoClienteDestinatarioDescCorta() != null) {
            Parametria parametriaClienteDestino = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_TIPO_CLIENTE, pricingDTO.getTipoClienteDestinatarioDescCorta());
            pricing.setTipoClienteDestinatario(parametriaClienteDestino);
        }

        if (pricingDTO.getTipoClienteOriginanteDescCorta() != null) {
            Parametria parametriaClienteOrigen = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_TIPO_CLIENTE, pricingDTO.getTipoClienteOriginanteDescCorta());
            pricing.setTipoClienteOriginante(parametriaClienteOrigen);
        }

        if (pricingDTO.getMonedaDescCorta() != null) {
            Parametria parametriaMoneda = parametriaRepository.findByIdCodigoTablaAndDescripcionCorta(Constants.CODIGO_TABLA_MONEDA, pricingDTO.getMonedaDescCorta());
            pricing.setMoneda(parametriaMoneda);
        }
    }
}
