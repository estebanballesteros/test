package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.MakerAndChecker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MakerAndChecker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MakerAndCheckerRepository extends JpaRepository<MakerAndChecker, Long> {

}
