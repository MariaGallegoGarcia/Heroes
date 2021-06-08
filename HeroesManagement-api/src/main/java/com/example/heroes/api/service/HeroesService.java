package com.example.heroes.api.service;

import com.example.heroes.api.service.domain.Heroe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroesService {

  /**
   * 
   * @param id
   * @return
   */
  Mono<Void> delete(int id);

  Flux<Heroe> getAll();

  Mono<Heroe> getById(int id);

  Flux<Heroe> getAllByPartialName(String partialName);

  Mono<Void> update(Heroe heroe);

  Mono<Void> save(Heroe heroe);

}
