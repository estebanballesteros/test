package com.octagon.costooperacion.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class FiltroParametriaDTO{

    private String canalOperacionDescCorta;
    private String monedaDescCorta;
    private String tipoClienteOriginanteDescCorta;
    private String tipoClienteDestinatarioDescCorta;
    private String tipoTransaccionDescCorta;
    private String formaPagoDescCorta;

    public FiltroParametriaDTO() {}

    public FiltroParametriaDTO(String canalOperacionDescCorta, String monedaDescCorta, String tipoClienteOriginanteDescCorta, String tipoClienteDestinatarioDescCorta, String tipoTransaccionDescCorta, String formaPagoDescCorta) {
        this.canalOperacionDescCorta = canalOperacionDescCorta;
        this.monedaDescCorta = monedaDescCorta;
        this.tipoClienteOriginanteDescCorta = tipoClienteOriginanteDescCorta;
        this.tipoClienteDestinatarioDescCorta = tipoClienteDestinatarioDescCorta;
        this.tipoTransaccionDescCorta = tipoTransaccionDescCorta;
        this.formaPagoDescCorta = formaPagoDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta del canal de operacion", allowableValues = "APP,ATM", example = "APP", required = true)
    public String getCanalOperacionDescCorta() {
        return canalOperacionDescCorta;
    }

    public void setCanalOperacionDescCorta(String canalOperacionDescCorta) {
        this.canalOperacionDescCorta = canalOperacionDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta del tipo de moneda", allowableValues = "ARS,USD", example = "ARS", required = false)
    public String getMonedaDescCorta() {
        return monedaDescCorta;
    }

    public void setMonedaDescCorta(String monedaDescCorta) {
        this.monedaDescCorta = monedaDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta tipo cliente", allowableValues = "R,P", example = "R", required = false)
    public String getTipoClienteOriginanteDescCorta() {
        return tipoClienteOriginanteDescCorta;
    }

    public void setTipoClienteOriginanteDescCorta(String tipoClienteOriginanteDescCorta) {
        this.tipoClienteOriginanteDescCorta = tipoClienteOriginanteDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta tipo cliente", allowableValues = "R,P", example = "R", required = false)
    public String getTipoClienteDestinatarioDescCorta() {
        return tipoClienteDestinatarioDescCorta;
    }

    public void setTipoClienteDestinatarioDescCorta(String tipoClienteDestinatarioDescCorta) {
        this.tipoClienteDestinatarioDescCorta = tipoClienteDestinatarioDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta transaccion", allowableValues = "TRA,PRE", required = true)
    public String getTipoTransaccionDescCorta() {
        return tipoTransaccionDescCorta;
    }

    public void setTipoTransaccionDescCorta(String tipoTransaccionDescCorta) {
        this.tipoTransaccionDescCorta = tipoTransaccionDescCorta;
    }

    @ApiModelProperty(notes = "Descripcion corta de la forma de pago", allowableValues = "EFECT,DEB,CRED,", example = "TRA", required = false)
    public String getFormaPagoDescCorta() {
        return formaPagoDescCorta;
    }

    public void setFormaPagoDescCorta(String formaPagoDescCorta) {
        this.formaPagoDescCorta = formaPagoDescCorta;
    }
}
