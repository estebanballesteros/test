package com.octagon.costooperacion.service.dto;

public class MakerAndCheckerDTO {

    private Long id;

    private Long usuarioId;

    private Long usuarioSupervisorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioSupervisorId() {
        return usuarioSupervisorId;
    }

    public void setUsuarioSupervisorId(Long usuarioSupervisorId) {
        this.usuarioSupervisorId = usuarioSupervisorId;
    }
}
