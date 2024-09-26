package com.example.reto_final.domain.repositories;

import com.example.reto_final.domain.Cashout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CashoutRepository extends ReactiveCrudRepository<Cashout, Long> {
    Flux<Cashout> findByUserId(Long userId);
}
