package com.octagon.costooperacion.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Pricing.
 */
@Entity
@Table(name = "pricing")
public class Pricing implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "condicion_menor_igual", precision = 10, scale = 2)
    private BigDecimal condicionMenorIgual;

    @Column(name = "condicion_mayor_igual", precision = 10, scale = 2)
    private BigDecimal condicionMayorIgual;

    @Column(name = "condicion_monto_desde", precision = 10, scale = 2)
    private BigDecimal condicionMontoDesde;

    @Column(name = "condicion_monto_hasta", precision = 10, scale = 2)
    private BigDecimal condicionMontoHasta;

    @OneToOne
    @JoinColumn(unique = true)
    private MakerAndChecker makerAndChecker;

    @OneToOne
    @JoinColumn(unique = true)
    private Costo costo;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria tipoTransaccion;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria canalOperacion;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria tipoClienteOriginante;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria tipoClienteDestinatario;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria moneda;

    @ManyToOne
    @JsonIgnoreProperties("pricings")
    private Parametria formaPago;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAprobado() {
        return aprobado;
    }

    public Pricing aprobado(Boolean aprobado) {
        this.aprobado = aprobado;
        return this;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public BigDecimal getCondicionMenorIgual() {
        return condicionMenorIgual;
    }

    public Pricing condicionMenorIgual(BigDecimal condicionMenorIgual) {
        this.condicionMenorIgual = condicionMenorIgual;
        return this;
    }

    public void setCondicionMenorIgual(BigDecimal condicionMenorIgual) {
        this.condicionMenorIgual = condicionMenorIgual;
    }

    public BigDecimal getCondicionMayorIgual() {
        return condicionMayorIgual;
    }

    public Pricing condicionMayorIgual(BigDecimal condicionMayorIgual) {
        this.condicionMayorIgual = condicionMayorIgual;
        return this;
    }

    public void setCondicionMayorIgual(BigDecimal condicionMayorIgual) {
        this.condicionMayorIgual = condicionMayorIgual;
    }

    public BigDecimal getCondicionMontoDesde() {
        return condicionMontoDesde;
    }

    public Pricing condicionMontoDesde(BigDecimal condicionMontoDesde) {
        this.condicionMontoDesde = condicionMontoDesde;
        return this;
    }

    public void setCondicionMontoDesde(BigDecimal condicionMontoDesde) {
        this.condicionMontoDesde = condicionMontoDesde;
    }

    public BigDecimal getCondicionMontoHasta() {
        return condicionMontoHasta;
    }

    public Pricing condicionMontoHasta(BigDecimal condicionMontoHasta) {
        this.condicionMontoHasta = condicionMontoHasta;
        return this;
    }

    public void setCondicionMontoHasta(BigDecimal condicionMontoHasta) {
        this.condicionMontoHasta = condicionMontoHasta;
    }

    public MakerAndChecker getMakerAndChecker() {
        return makerAndChecker;
    }

    public Pricing makerAndChecker(MakerAndChecker makerAndChecker) {
        this.makerAndChecker = makerAndChecker;
        return this;
    }

    public void setMakerAndChecker(MakerAndChecker makerAndChecker) {
        this.makerAndChecker = makerAndChecker;
    }

    public Costo getCosto() {
        return costo;
    }

    public Pricing costo(Costo costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public Parametria getTipoTransaccion() {
        return tipoTransaccion;
    }

    public Pricing tipoTransaccion(Parametria parametria) {
        this.tipoTransaccion = parametria;
        return this;
    }

    public void setTipoTransaccion(Parametria parametria) {
        this.tipoTransaccion = parametria;
    }

    public Parametria getCanalOperacion() {
        return canalOperacion;
    }

    public Pricing canalOperacion(Parametria parametria) {
        this.canalOperacion = parametria;
        return this;
    }

    public void setCanalOperacion(Parametria parametria) {
        this.canalOperacion = parametria;
    }

    public Parametria getTipoClienteOriginante() {
        return tipoClienteOriginante;
    }

    public Pricing tipoClienteOriginante(Parametria parametria) {
        this.tipoClienteOriginante = parametria;
        return this;
    }

    public void setTipoClienteOriginante(Parametria parametria) {
        this.tipoClienteOriginante = parametria;
    }

    public Parametria getTipoClienteDestinatario() {
        return tipoClienteDestinatario;
    }

    public Pricing tipoClienteDestinatario(Parametria parametria) {
        this.tipoClienteDestinatario = parametria;
        return this;
    }

    public void setTipoClienteDestinatario(Parametria parametria) {
        this.tipoClienteDestinatario = parametria;
    }

    public Parametria getMoneda() {
        return moneda;
    }

    public Pricing moneda(Parametria parametria) {
        this.moneda = parametria;
        return this;
    }

    public void setMoneda(Parametria parametria) {
        this.moneda = parametria;
    }

    public Parametria getFormaPago() {
        return formaPago;
    }

    public Pricing formaPago(Parametria parametria) {
        this.formaPago = parametria;
        return this;
    }

    public void setFormaPago(Parametria parametria) {
        this.formaPago = parametria;
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
        Pricing pricing = (Pricing) o;
        if (pricing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pricing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pricing{" +
            "id=" + getId() +
            ", aprobado='" + isAprobado() + "'" +
            ", condicionMenorIgual=" + getCondicionMenorIgual() +
            ", condicionMayorIgual=" + getCondicionMayorIgual() +
            ", condicionMontoDesde=" + getCondicionMontoDesde() +
            ", condicionMontoHasta=" + getCondicionMontoHasta() +
            "}";
    }
}
