package com.octagon.costooperacion.web.rest;

import com.octagon.costooperacion.domain.DollarExchange;
import com.octagon.costooperacion.service.DollarExchangeService;
import com.octagon.costooperacion.service.dto.DollarExchangeDTO;
import com.octagon.costooperacion.service.dto.ResponseDTO;
import com.octagon.costooperacion.service.utills.ResponseStatus;
import com.octagon.costooperacion.web.rest.util.HeaderUtil;
import com.octagon.costooperacion.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing DollarExchange
 */
@RestController
@RequestMapping("/api")
public class DollarExchangeResource {

    private final Logger log = LoggerFactory.getLogger(DollarExchangeResource.class);

    private static final String ENTITY_NAME = "DollarExchangeResource";

    private final DollarExchangeService dollarExchangeService;

    public DollarExchangeResource(DollarExchangeService dollarExchangeService) {
        this.dollarExchangeService = dollarExchangeService;
    }

    /**
     * POST /dollar_exchange: update the value for dollar exchange
     * @param dollarExchangeDTO the value for dollar_exchange
     * @return the ResponseEntity with status 201 (Created) and with body the new dollar_exchange
     * or with status 500 (Internal Server Error)
     */
    @PostMapping("/dollar_exchange")
    @ApiOperation(value = "Permite generar un nuevo registro para la cotizacion del dolar con respecto a pesos argentinos")
    public ResponseEntity<ResponseDTO<DollarExchangeDTO>> updateDollarExchange(@RequestBody DollarExchangeDTO dollarExchangeDTO) {
        log.debug("REST request to save DollarExchange : {}", dollarExchangeDTO);
        DollarExchangeDTO result;
        try {
            result = dollarExchangeService.save(dollarExchangeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseDTO(false, e));
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getMonto().toString()))
            .body(new ResponseDTO<>(true, ResponseStatus.CREATED));
    }

    /**
     * GET /dollar_exchange
     * @return the ResponseEntity with status 200 (ok)
     * or with status 500 (Internal Server Error)
     */
    @GetMapping("/dollar_exchange")
    @ApiOperation(value = "Permite obtener la cotizacion actual del dolar con respecto al peso")
    public ResponseEntity<ResponseDTO<DollarExchangeDTO>> getCurrentExchange() {
        log.debug("REST request to get current DollarExchange : {}");
        try {
            DollarExchangeDTO dollarExchangeDTO = dollarExchangeService.getCurrentExchange();
            ResponseDTO<DollarExchangeDTO> responseDTO = new ResponseDTO<>(true, ResponseStatus.OK);
            responseDTO.setResponseData(dollarExchangeDTO);
            return ResponseEntity
                .ok()
                .body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseDTO(false, e));
        }
    }

    /**
     * GET /dollar_exchange/history
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dollar_exchange in body
     */
    @GetMapping("/dollar_exchange/history")
    @ApiOperation(value = "Obtiene todas las cotizaciones del dolar")
    public ResponseEntity<ResponseDTO<List<DollarExchange>>> getAll(Pageable pageable) {
        Page<DollarExchange> page;
        try {
            page = dollarExchangeService.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseDTO(false, e));
        }
        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK);
        responseDTO.setResponseData(page.getContent());
        return ResponseEntity
            .ok()
            .headers(PaginationUtil.generatePaginationHttpHeaders(page, "/api/dollar_exchange"))
            .body(responseDTO);
    }
}
