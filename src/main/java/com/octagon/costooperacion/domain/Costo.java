package com.octagon.costooperacion.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Costo.
 */
@Entity
@Table(name = "costo")
public class Costo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "monto_dolares", precision = 10, scale = 2)
    private BigDecimal montoDolares;

    @Column(name = "porcentaje", precision = 10, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "iva", precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(name = "total_dolares", precision = 10, scale = 2)
    private BigDecimal totalDolares;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontoDolares() {
        return montoDolares;
    }

    public Costo montoDolares(BigDecimal montoDolares) {
        this.montoDolares = montoDolares;
        return this;
    }

    public void setMontoDolares(BigDecimal montoDolares) {
        this.montoDolares = montoDolares;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public Costo porcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public Costo iva(BigDecimal iva) {
        this.iva = iva;
        return this;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotalDolares() {
        return totalDolares;
    }

    public Costo totalDolares(BigDecimal totalDolares) {
        this.totalDolares = totalDolares;
        return this;
    }

    public void setTotalDolares(BigDecimal totalDolares) {
        this.totalDolares = totalDolares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Costo costo = (Costo) o;
        if (costo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Costo{" +
            "id=" + getId() +
            ", montoDolares=" + getMontoDolares() +
            ", porcentaje=" + getPorcentaje() +
            ", iva=" + getIva() +
            ", totalDolares=" + getTotalDolares() +
            "}";
    }
}
