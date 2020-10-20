package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.Parametria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Parametria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametriaRepository extends JpaRepository<Parametria, Long> {

    Parametria findByIdCodigoTablaAndDescripcionCorta(Integer codigoTabla, String DescripcionCorta);

}
