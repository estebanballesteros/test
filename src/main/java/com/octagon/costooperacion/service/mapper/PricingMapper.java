package com.octagon.costooperacion.service.mapper;

import com.octagon.costooperacion.domain.Parametria;
import com.octagon.costooperacion.domain.Pricing;
import com.octagon.costooperacion.service.dto.PricingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Parametria.class})
public interface PricingMapper extends EntityMapper<PricingDTO, Pricing> {

    @Mapping(source = "tipoTransaccion.descripcionLarga", target = "tipoTransaccionDescLarga")
    @Mapping(source = "tipoTransaccion.descripcionCorta", target = "tipoTransaccionDescCorta")
    @Mapping(source = "canalOperacion.descripcionLarga", target = "canalDescLarga")
    @Mapping(source = "canalOperacion.descripcionCorta", target = "canalDescCorta")
    @Mapping(source = "tipoClienteOriginante.descripcionLarga", target = "tipoClienteOriginanteDescLarga")
    @Mapping(source = "tipoClienteOriginante.descripcionCorta", target = "tipoClienteOriginanteDescCorta")
    @Mapping(source = "tipoClienteDestinatario.descripcionLarga", target = "tipoClienteDestinatarioDescLarga")
    @Mapping(source = "tipoClienteDestinatario.descripcionCorta", target = "tipoClienteDestinatarioDescCorta")
    @Mapping(source = "moneda.descripcionLarga", target = "monedaDescLarga")
    @Mapping(source = "moneda.descripcionCorta", target = "monedaDescCorta")
    @Mapping(source = "formaPago.descripcionLarga", target = "formaPagoDescLarga")
    @Mapping(source = "formaPago.descripcionCorta", target = "formaPagoDescCorta")
    @Mapping(source = "costo.montoDolares", target="montoDolares")
    @Mapping(source = "costo.porcentaje", target="porcentaje")
    @Mapping(source = "costo.iva", target="iva")
    @Mapping(source = "costo.totalDolares", target="totalDolares")
    PricingDTO toDto(Pricing pricing);

    @Mapping(target = "tipoTransaccion", ignore = true)
    @Mapping(target = "canalOperacion", ignore = true)
    @Mapping(target = "tipoClienteOriginante", ignore = true)
    @Mapping(target = "tipoClienteDestinatario", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    Pricing toEntity(PricingDTO pricingDTO);

}
