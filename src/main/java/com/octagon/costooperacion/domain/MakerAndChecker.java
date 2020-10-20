package com.octagon.costooperacion.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MakerAndChecker.
 */
@Entity
@Table(name = "maker_and_checker")
public class MakerAndChecker implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "usuario_supervisor_id")
    private Long usuarioSupervisorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public MakerAndChecker usuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioSupervisorId() {
        return usuarioSupervisorId;
    }

    public MakerAndChecker usuarioSupervisorId(Long usuarioSupervisorId) {
        this.usuarioSupervisorId = usuarioSupervisorId;
        return this;
    }

    public void setUsuarioSupervisorId(Long usuarioSupervisorId) {
        this.usuarioSupervisorId = usuarioSupervisorId;
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
        MakerAndChecker makerAndChecker = (MakerAndChecker) o;
        if (makerAndChecker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), makerAndChecker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MakerAndChecker{" +
            "id=" + getId() +
            ", usuarioId=" + getUsuarioId() +
            ", usuarioSupervisorId=" + getUsuarioSupervisorId() +
            "}";
    }
}
