package com.example.heroes.api.service;

import org.springframework.stereotype.Component;
import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.model.entity.HeroeEntity;

@Component
public class HeroesDomainMapper implements GenericDOMapper<Heroe, HeroeEntity> {

  @Override
  public Heroe toDO(HeroeEntity heroeEntity) {
    return Heroe
        .builder()
        .id(heroeEntity.getId())
        .name(heroeEntity.getName())
        .build();
  }

  @Override
  public HeroeEntity fromDO(Heroe heroe) {
    return HeroeEntity
        .builder()
        .id(heroe.getId())
        .name(heroe.getName())
        .build();
  }

  
}
