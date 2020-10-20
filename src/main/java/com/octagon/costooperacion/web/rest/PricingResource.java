package com.octagon.costooperacion.web.rest;
import com.octagon.costooperacion.domain.Parametria;
import com.octagon.costooperacion.domain.Pricing;
import com.octagon.costooperacion.security.AuthoritiesConstants;
import com.octagon.costooperacion.service.PricingService;
import com.octagon.costooperacion.service.dto.FiltroParametriaDTO;
import com.octagon.costooperacion.service.dto.ParametriaDTO;
import com.octagon.costooperacion.service.dto.PricingDTO;
import com.octagon.costooperacion.service.dto.ResponseDTO;
import com.octagon.costooperacion.service.utills.OctagonBusinessException;
import com.octagon.costooperacion.service.utills.ResponseStatus;
import com.octagon.costooperacion.web.rest.errors.BadRequestAlertException;
import com.octagon.costooperacion.web.rest.util.HeaderUtil;
import com.octagon.costooperacion.web.rest.util.PaginationUtil;
import feign.Param;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pricing.
 */
@RestController
@RequestMapping("/api")
public class PricingResource {

    private final Logger log = LoggerFactory.getLogger(PricingResource.class);

    private static final String ENTITY_NAME = "PricingResource";

    private final PricingService pricingService;

    public PricingResource(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * POST  /pricings : Create a new pricing.
     *
     * @param pricingDTO the pricing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pricing, or with status 400 (Bad Request) if the pricing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pricings")
    @ApiOperation(value = "Permite crear un Pricing asociado a los parámetros indicados")
    public ResponseEntity<ResponseDTO<PricingDTO>> createPricing(@RequestBody PricingDTO pricingDTO) throws URISyntaxException {
        log.debug("REST request to save Pricing : {}", pricingDTO);
        if (pricingDTO.getId() != null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, ResponseStatus.BAD_REQUEST, "A new pricing cannot already have an ID"));
        }
        PricingDTO result;
        try {
            result = pricingService.save(pricingDTO);
        } catch (OctagonBusinessException e) {
            e.printStackTrace();
            return ResponseEntity
                .ok(e.getResponseDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseDTO(false, e));
        }
        return ResponseEntity
            .created(new URI("/costooperacion/api/pricing/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(new ResponseDTO<>(true, ResponseStatus.CREATED));
    }

    /**
     * PUT  /pricings : Updates an existing pricing.
     *
     * @param pricingDTO the pricing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pricing,
     * or with status 400 (Bad Request) if the pricing is not valid,
     * or with status 500 (Internal Server Error) if the pricing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pricings")
    @ApiOperation(value = "Actualiza un pricing indicado")
    public ResponseEntity<ResponseDTO<PricingDTO>> updatePricing(@RequestBody PricingDTO pricingDTO) {
        log.debug("REST request to update Pricing : {}", pricingDTO);
        if (pricingDTO.getId() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, ResponseStatus.BAD_REQUEST, "Invalid id"));
        }
        PricingDTO result;
        try {
            result = pricingService.save(pricingDTO);
        } catch (OctagonBusinessException e) {
            e.printStackTrace();
            return ResponseEntity
                .ok(e.getResponseDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .unprocessableEntity()
                .body(new ResponseDTO(false, e));
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(new ResponseDTO<>(true, ResponseStatus.OK));
    }

    /**
     * GET  /pricings : get all the pricings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pricings in body
     */
    @GetMapping("/pricings")
    @ApiOperation(value = "Obtiene todos los Pricing")
    public ResponseEntity<ResponseDTO<List<PricingDTO>>> getAllPricings(Pageable pageable) {
        log.debug("REST request to get a page of Pricings");
        Page<PricingDTO> page;
        try {
            page = pricingService.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, e));
        }
        ResponseDTO responseDTO = new ResponseDTO(true, ResponseStatus.OK);
        responseDTO.setResponseData(page.getContent());
        return ResponseEntity
            .ok()
            .headers(PaginationUtil.generatePaginationHttpHeaders(page, "/api/pricings"))
            .body(responseDTO);

    }

    /**
     * DELETE  /pricings/:id : delete the "id" pricing.
     *
     * @param id the id of the pricing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pricings/{id}")
    @ApiOperation(value = "Elimina el Pricing indicado")
    public ResponseEntity<ResponseDTO> deletePricing(@PathVariable Long id) {
        log.debug("REST request to delete Pricing : {}", id);
        try {
            pricingService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (OctagonBusinessException e) {
            e.printStackTrace();
            return ResponseEntity
                .badRequest()
                .body(new ResponseDTO(false, e));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, e));
        }
    }

    @GetMapping("/pricings/parametria")
    @ApiOperation(value = "Permite obtener el Pricing a partir de la Parametría indicada", notes = "Utilizado desde ms-prestamos y ms-cuenta-transaccion")
    public ResponseEntity<ResponseDTO<PricingDTO>> getPricingByParametros(@Param("filtroParametriaDTO") FiltroParametriaDTO filtroParametriaDTO) {
        log.debug("REST request to delete Pricing : {}");
        try {
            PricingDTO dto = pricingService.getPricingByParametria(filtroParametriaDTO);
            ResponseDTO<PricingDTO> responseDTO = new ResponseDTO<>(true, ResponseStatus.OK);
            responseDTO.setResponseData(dto);
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
