package com.octagon.costooperacion.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PricingDTO implements Serializable {

    private Long id;
    private Boolean aprobado;
    private BigDecimal condicionMenorIgual;
    private BigDecimal condicionMayorIgual;
    private BigDecimal condicionMontoDesde;
    private BigDecimal condicionMontoHasta;
    private String tipoTransaccionDescLarga;
    private String tipoTransaccionDescCorta;
    private String canalDescLarga;
    private String canalDescCorta;
    private String tipoClienteOriginanteDescLarga;
    private String tipoClienteOriginanteDescCorta;
    private String tipoClienteDestinatarioDescLarga;
    private String tipoClienteDestinatarioDescCorta;
    private String monedaDescLarga;
    private String monedaDescCorta;
    private String formaPagoDescLarga;
    private String formaPagoDescCorta;

    private BigDecimal montoDolares;
    private BigDecimal montoPesosARS;
    private BigDecimal porcentaje;
    private BigDecimal iva;
    private BigDecimal totalDolares;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public BigDecimal getCondicionMenorIgual() {
        return condicionMenorIgual;
    }

    public void setCondicionMenorIgual(BigDecimal condicionMenorIgual) {
        this.condicionMenorIgual = condicionMenorIgual;
    }

    public BigDecimal getCondicionMayorIgual() {
        return condicionMayorIgual;
    }

    public void setCondicionMayorIgual(BigDecimal condicionMayorIgual) {
        this.condicionMayorIgual = condicionMayorIgual;
    }

    public BigDecimal getCondicionMontoDesde() {
        return condicionMontoDesde;
    }

    public void setCondicionMontoDesde(BigDecimal condicionMontoDesde) {
        this.condicionMontoDesde = condicionMontoDesde;
    }

    public BigDecimal getCondicionMontoHasta() {
        return condicionMontoHasta;
    }

    public void setCondicionMontoHasta(BigDecimal condicionMontoHasta) {
        this.condicionMontoHasta = condicionMontoHasta;
    }

    public String getTipoTransaccionDescLarga() {
        return tipoTransaccionDescLarga;
    }

    public void setTipoTransaccionDescLarga(String tipoTransaccionDescLarga) {
        this.tipoTransaccionDescLarga = tipoTransaccionDescLarga;
    }

    public String getTipoTransaccionDescCorta() {
        return tipoTransaccionDescCorta;
    }

    public void setTipoTransaccionDescCorta(String tipoTransaccionDescCorta) {
        this.tipoTransaccionDescCorta = tipoTransaccionDescCorta;
    }

    public String getCanalDescLarga() {
        return canalDescLarga;
    }

    public void setCanalDescLarga(String canalDescLarga) {
        this.canalDescLarga = canalDescLarga;
    }

    public String getCanalDescCorta() {
        return canalDescCorta;
    }

    public void setCanalDescCorta(String canalDescCorta) {
        this.canalDescCorta = canalDescCorta;
    }

    public String getTipoClienteOriginanteDescLarga() {
        return tipoClienteOriginanteDescLarga;
    }

    public void setTipoClienteOriginanteDescLarga(String tipoClienteOriginanteDescLarga) {
        this.tipoClienteOriginanteDescLarga = tipoClienteOriginanteDescLarga;
    }

    public String getTipoClienteOriginanteDescCorta() {
        return tipoClienteOriginanteDescCorta;
    }

    public void setTipoClienteOriginanteDescCorta(String tipoClienteOriginanteDescCorta) {
        this.tipoClienteOriginanteDescCorta = tipoClienteOriginanteDescCorta;
    }

    public String getTipoClienteDestinatarioDescLarga() {
        return tipoClienteDestinatarioDescLarga;
    }

    public void setTipoClienteDestinatarioDescLarga(String tipoClienteDestinatarioDescLarga) {
        this.tipoClienteDestinatarioDescLarga = tipoClienteDestinatarioDescLarga;
    }

    public String getTipoClienteDestinatarioDescCorta() {
        return tipoClienteDestinatarioDescCorta;
    }

    public void setTipoClienteDestinatarioDescCorta(String tipoClienteDestinatarioDescCorta) {
        this.tipoClienteDestinatarioDescCorta = tipoClienteDestinatarioDescCorta;
    }

    public String getMonedaDescLarga() {
        return monedaDescLarga;
    }

    public void setMonedaDescLarga(String monedaDescLarga) {
        this.monedaDescLarga = monedaDescLarga;
    }

    public String getMonedaDescCorta() {
        return monedaDescCorta;
    }

    public void setMonedaDescCorta(String monedaDescCorta) {
        this.monedaDescCorta = monedaDescCorta;
    }

    public String getFormaPagoDescLarga() {
        return formaPagoDescLarga;
    }

    public void setFormaPagoDescLarga(String formaPagoDescLarga) {
        this.formaPagoDescLarga = formaPagoDescLarga;
    }

    public String getFormaPagoDescCorta() {
        return formaPagoDescCorta;
    }

    public void setFormaPagoDescCorta(String formaPagoDescCorta) {
        this.formaPagoDescCorta = formaPagoDescCorta;
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

    public BigDecimal getMontoPesosARS() {
        return montoPesosARS;
    }

    public void setMontoPesosARS(BigDecimal montoPesosARS) {
        this.montoPesosARS = montoPesosARS;
    }
}
