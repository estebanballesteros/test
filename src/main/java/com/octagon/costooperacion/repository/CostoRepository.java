package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.Costo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Costo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostoRepository extends JpaRepository<Costo, Long> {

}
