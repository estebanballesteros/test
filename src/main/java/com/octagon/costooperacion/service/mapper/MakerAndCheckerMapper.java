package com.octagon.costooperacion.service.mapper;

import com.octagon.costooperacion.domain.MakerAndChecker;
import com.octagon.costooperacion.service.dto.MakerAndCheckerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { MakerAndChecker.class })
public interface MakerAndCheckerMapper extends EntityMapper<MakerAndCheckerDTO, MakerAndChecker> {

    MakerAndCheckerDTO toDto(MakerAndChecker makerAndChecker);

    MakerAndChecker toEntity(MakerAndCheckerDTO makerAndCheckerDTO);

}
