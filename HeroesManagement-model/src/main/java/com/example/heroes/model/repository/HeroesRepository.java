package com.example.heroes.model.repository;

import com.example.heroes.model.entity.HeroeEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroesRepository extends ReactiveCrudRepository<HeroeEntity, Integer> {
  
  @Query("SELECT * FROM heroe WHERE name LIKE CONCAT('%',?1,'%')")
  Flux<HeroeEntity> getAllByPartialName(String name);

  @Modifying
  @Query("UPDATE heroe set realName = :realName where name = :name")
  Mono<Integer> update(String realName, String name);  
}
