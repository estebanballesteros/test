package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.Parametria;
import com.octagon.costooperacion.domain.Pricing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {

    Pricing findByCanalOperacionAndMonedaAndTipoClienteDestinatarioAndTipoClienteOriginanteAndTipoTransaccionAndFormaPago(
        Parametria canalOperacion,
        Parametria moneda,
        Parametria tipoClienteDestinatario,
        Parametria tipoClienteOriginante,
        Parametria tipoTransaccion,
        Parametria formaPago
    );



}
