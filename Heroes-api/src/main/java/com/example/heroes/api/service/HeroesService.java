package com.example.heroes.api.service;

import com.example.heroes.api.service.domain.Heroe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroesService {

  Mono<Void> delete(int id);

  Mono<Void> upsert(Heroe heroe);

  Flux<Heroe> getAll();

  Mono<Heroe> getById(int id);

  Flux<Heroe> getAllByPartialName(String partialName);

}
