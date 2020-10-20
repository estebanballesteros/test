package com.octagon.costooperacion.web.rest;
import com.octagon.costooperacion.domain.PricingHistory;
import com.octagon.costooperacion.service.PricingHistoryService;
import com.octagon.costooperacion.service.dto.PricingHistoryDTO;
import com.octagon.costooperacion.service.dto.ResponseDTO;
import com.octagon.costooperacion.service.utills.ResponseStatus;
import com.octagon.costooperacion.web.rest.errors.BadRequestAlertException;
import com.octagon.costooperacion.web.rest.util.HeaderUtil;
import com.octagon.costooperacion.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PricingHistory.
 */
@RestController
@RequestMapping("/api")
public class PricingHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PricingHistoryResource.class);

    private static final String ENTITY_NAME = "costooperacionPricingHistory";

    private final PricingHistoryService pricingHistoryService;

    public PricingHistoryResource(PricingHistoryService pricingHistoryService) {
        this.pricingHistoryService = pricingHistoryService;
    }

    /**
     * GET  /pricing-histories : get all the pricingHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pricingHistories in body
     */
    @GetMapping("/pricing-histories")
    @ApiOperation(value = "Obtiene todos los Pricings")
    public ResponseEntity<ResponseDTO<List<PricingHistoryDTO>>> getAllPricingHistories(Pageable pageable) {
        log.debug("REST request to get a page of PricingHistories");
        Page<PricingHistory> page;
        try {
            page = pricingHistoryService.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, e));
        }

        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK);
        responseDTO.setResponseData(ResponseEntity.ok().headers(PaginationUtil.generatePaginationHttpHeaders(page, "/api/pricing-histories")).body(page.getContent()));
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    /**
     * GET  /pricing-histories/:id : get the "id" pricingHistory.
     *
     * @param id the id of the pricingHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pricingHistory, or with status 404 (Not Found)
     */
    @GetMapping("/pricing-histories/{id}")
    @ApiOperation(value = "Obtiene el historico de Pricing asociado a una parametria")
    public ResponseEntity<ResponseDTO<PricingHistoryDTO>> getPricingHistory(@PathVariable Long id) {
        log.debug("REST request to get PricingHistory : {}", id);
        try {
            PricingHistoryDTO pricingHistory = pricingHistoryService.findOne(id);
            ResponseDTO<PricingHistoryDTO> responseDTO = new ResponseDTO<>(true, ResponseStatus.OK);
            responseDTO.setResponseData(pricingHistory);
            return ResponseEntity
                .ok()
                .body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, e));
        }

    }
}
