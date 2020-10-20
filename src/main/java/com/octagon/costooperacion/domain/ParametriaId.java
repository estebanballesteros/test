package com.octagon.costooperacion.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ParametriaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "codigo_tabla")
    private Integer codigoTabla;

    @Column(name = "codigo_item")
    private Integer codigoItem;

    public ParametriaId() {
    }

    public ParametriaId(Integer codigoTabla, Integer codigoItem) {
        this.codigoTabla = codigoTabla;
        this.codigoItem = codigoItem;
    }

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
}
