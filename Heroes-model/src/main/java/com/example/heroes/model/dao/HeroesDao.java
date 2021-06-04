package com.example.heroes.model.dao;

import com.example.heroes.model.entity.HeroeEntity;
import reactor.core.publisher.Flux;

public interface HeroesDao {

  public Flux<HeroeEntity> getAllByPartialName(String partialName);
}
