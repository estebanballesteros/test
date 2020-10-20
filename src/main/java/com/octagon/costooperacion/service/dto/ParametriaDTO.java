package com.octagon.costooperacion.service.dto;

import java.io.Serializable;

public class ParametriaDTO implements Serializable {

    private Integer codigoTabla;

    private Integer codigoItem;

    private String descripcionLarga;

    private String descripcionCorta;

    private String grupo1;

    private String grupo2;

    public Integer getCodigoTabla() {
        return codigoTabla;
    }

    public void setCodigoTabla(Integer codigoTabla) {
        this.codigoTabla = codigoTabla;
    }

    public Integer getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(Integer codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getGrupo1() {
        return grupo1;
    }

    public void setGrupo1(String grupo1) {
        this.grupo1 = grupo1;
    }

    public String getGrupo2() {
        return grupo2;
    }

    public void setGrupo2(String grupo2) {
        this.grupo2 = grupo2;
    }
}
