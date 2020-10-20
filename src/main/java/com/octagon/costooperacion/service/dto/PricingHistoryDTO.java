package com.octagon.costooperacion.service.dto;

import com.octagon.costooperacion.domain.Costo;
import com.octagon.costooperacion.domain.MakerAndChecker;
import com.octagon.costooperacion.domain.Pricing;

import java.io.Serializable;
import java.time.Instant;

public class PricingHistoryDTO implements Serializable {

    private Long id;
    private Instant startDate;
    private Instant endDate;
    private String notaActualizacion;
    private MakerAndCheckerDTO makerAndCheckerDTO;
    private CostoDTO costoDTO;
    private PricingDTO pricingDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getNotaActualizacion() {
        return notaActualizacion;
    }

    public void setNotaActualizacion(String notaActualizacion) {
        this.notaActualizacion = notaActualizacion;
    }

    public MakerAndCheckerDTO getMakerAndCheckerDTO() {
        return makerAndCheckerDTO;
    }

    public void setMakerAndCheckerDTO(MakerAndCheckerDTO makerAndCheckerDTO) {
        this.makerAndCheckerDTO = makerAndCheckerDTO;
    }

    public CostoDTO getCostoDTO() {
        return costoDTO;
    }

    public void setCostoDTO(CostoDTO costoDTO) {
        this.costoDTO = costoDTO;
    }

    public PricingDTO getPricingDTO() {
        return pricingDTO;
    }

    public void setPricingDTO(PricingDTO pricingDTO) {
        this.pricingDTO = pricingDTO;
    }
}
