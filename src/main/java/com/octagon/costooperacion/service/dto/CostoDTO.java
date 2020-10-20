package com.octagon.costooperacion.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CostoDTO implements Serializable {

    private Long id;

    private BigDecimal montoDolares;

    private BigDecimal porcentaje;

    private BigDecimal iva;

    private BigDecimal totalDolares;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(BigDecimal montoDolares) {
        this.montoDolares = montoDolares;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalDolares() {
        return totalDolares;
    }

    public void setTotalDolares(BigDecimal totalDolares) {
        this.totalDolares = totalDolares;
    }


}
