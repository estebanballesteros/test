package com.octagon.costooperacion.service.mapper;

import com.octagon.costooperacion.domain.Costo;
import com.octagon.costooperacion.service.dto.CostoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { Costo.class })
public interface CostoMapper extends EntityMapper<CostoDTO, Costo> {

    public CostoDTO toDto(Costo costo);

    public Costo toEntity(CostoDTO costoDTO);

}
