package com.octagon.costooperacion.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Parametria.
 */
@Entity
@Table(name = "parametria")
public class Parametria implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ParametriaId id;

    @Column(name = "descripcion_larga")
    private String descripcionLarga;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    @Column(name = "grupo_1")
    private String grupo1;

    @Column(name = "grupo_2")
    private String grupo2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public ParametriaId getId() {
        return id;
    }

    public void setId(ParametriaId id) {
        this.id = id;
    }

    public Parametria parametriaId(ParametriaId id) {
        this.id = id;
        return this;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public Parametria descripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public Parametria descripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
        return this;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getGrupo1() {
        return grupo1;
    }

    public Parametria grupo1(String grupo1) {
        this.grupo1 = grupo1;
        return this;
    }

    public void setGrupo1(String grupo1) {
        this.grupo1 = grupo1;
    }

    public String getGrupo2() {
        return grupo2;
    }

    public Parametria grupo2(String grupo2) {
        this.grupo2 = grupo2;
        return this;
    }

    public void setGrupo2(String grupo2) {
        this.grupo2 = grupo2;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public String toString() {
        return "Parametria{" +
                "parametriaId=" + id + '\'' +
            "descripcionLarga=" + descripcionLarga + '\'' +
            "descripcionCorta=" + descripcionCorta + '\'' +
            "grupo1=" + grupo1 + '\'' +
            "grupo2=" + grupo2 + '\'' +
            "}";
    }


}
