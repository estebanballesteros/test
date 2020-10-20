package com.octagon.costooperacion.service.dto;

import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class DollarExchangeDTO implements Serializable {

    private BigDecimal monto;

    public DollarExchangeDTO(BigDecimal monto) {
        this.monto = monto;
    }

    public DollarExchangeDTO() {}

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
