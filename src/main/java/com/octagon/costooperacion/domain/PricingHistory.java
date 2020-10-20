package com.octagon.costooperacion.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PricingHistory.
 */
@Entity
@Table(name = "pricing_history")
public class PricingHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "nota_actualizacion")
    private String notaActualizacion;

    @OneToOne
    @JoinColumn(unique = true)
    private MakerAndChecker makerAndChecker;

    @OneToOne
    @JoinColumn(unique = true)
    private Costo costo;

    @ManyToOne
    @JoinColumn(name = "pricing_id")
    private Pricing pricing;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public PricingHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public PricingHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getNotaActualizacion() {
        return notaActualizacion;
    }

    public PricingHistory notaActualizacion(String notaActualizacion) {
        this.notaActualizacion = notaActualizacion;
        return this;
    }

    public void setNotaActualizacion(String notaActualizacion) {
        this.notaActualizacion = notaActualizacion;
    }

    public MakerAndChecker getMakerAndChecker() {
        return makerAndChecker;
    }

    public PricingHistory makerAndChecker(MakerAndChecker makerAndChecker) {
        this.makerAndChecker = makerAndChecker;
        return this;
    }

    public void setMakerAndChecker(MakerAndChecker makerAndChecker) {
        this.makerAndChecker = makerAndChecker;
    }

    public Costo getCosto() {
        return costo;
    }

    public PricingHistory costo(Costo costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
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
        PricingHistory pricingHistory = (PricingHistory) o;
        if (pricingHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pricingHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PricingHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", notaActualizacion='" + getNotaActualizacion() + "'" +
            "}";
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }
}
