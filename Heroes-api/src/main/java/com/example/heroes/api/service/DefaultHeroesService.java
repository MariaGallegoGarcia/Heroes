package com.example.heroes.api.service;

import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.model.repository.HeroesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultHeroesService implements HeroesService {

  private HeroesRepository repository;

  private HeroesDomainMapper mapper;

  @Override
  public Mono<Void> delete(int id) {
    return this.repository.deleteById(id);
  }

  @Override
  public Mono<Void> upsert(Heroe heroe) {
    return this.repository.save(mapper.fromDO(heroe)).then();
  }

  @Override
  public Flux<Heroe> getAll() {
    return this.repository.findAll().map(mapper::toDO);
  }

  @Override
  public Mono<Heroe> getById(int id) {
    return this.repository.findById(id).map(mapper::toDO);
  }

  @Override
  public Flux<Heroe> getAllByPartialName(String partialName) {
    return this.repository.getAllByPartialName(partialName).map(mapper::toDO);
  }
}
