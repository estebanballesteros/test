package com.octagon.costooperacion.repository;

import com.octagon.costooperacion.domain.DollarExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface DollarExchangeRepository extends JpaRepository<DollarExchange, Long> {

    @Query(value = "select top 1 * from dollar_exchange order by fecha desc", nativeQuery = true)
    DollarExchange findFirstOrderByFecha();
}
