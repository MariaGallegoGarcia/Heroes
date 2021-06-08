package com.example.heroes.api.service;

import com.example.heroes.api.service.domain.Heroe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroesService {

  /**
   * Deletes a heroe by id. 
   */
  Mono<Void> delete(int id);

  /**
   * Returns all the heroes.
   */
  Flux<Heroe> getAll();

  /**
   * Returns a heroe by id. 
   */
  Mono<Heroe> getById(int id);

  /**
   * Returns all the heroes searching by name.
   */
  Flux<Heroe> getAllByPartialName(String partialName);

  /**
   * Updates a heroe.
   */
  Mono<Void> update(Heroe heroe);

  /**
   * Saves a heroe.
   */
  Mono<Void> save(Heroe heroe);

}
