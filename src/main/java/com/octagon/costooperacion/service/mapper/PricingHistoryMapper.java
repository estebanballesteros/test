package com.octagon.costooperacion.service.mapper;

import com.octagon.costooperacion.domain.PricingHistory;
import com.octagon.costooperacion.service.dto.PricingHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PricingHistory.class})
public interface PricingHistoryMapper {

    PricingHistoryDTO toDto (PricingHistory pricingHistory);

    PricingHistory toEntity(PricingHistoryDTO pricingHistoryDTO);

}
