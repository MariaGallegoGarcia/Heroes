package com.example.heroes.model.repository;

import com.example.heroes.model.entity.HeroeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HeroesRepository extends ReactiveCrudRepository<HeroeEntity, Integer> {
  
  @Query("SELECT * FROM heroe WHERE name like '%:partialName%'")
  Flux<HeroeEntity> getAllByPartialName(String partialName);

}
