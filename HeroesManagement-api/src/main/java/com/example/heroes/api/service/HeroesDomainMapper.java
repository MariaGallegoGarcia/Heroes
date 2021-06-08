package com.example.heroes.api.service;

import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.model.entity.HeroeEntity;
import org.springframework.stereotype.Component;

@Component
public class HeroesDomainMapper implements GenericDOMapper<Heroe, HeroeEntity> {

  @Override
  public Heroe toDO(HeroeEntity heroeEntity) {
    return Heroe
        .builder()
        .id(heroeEntity.getId())
        .name(heroeEntity.getName())
        .realName(heroeEntity.getRealName())
        .build();
  }

  @Override
  public HeroeEntity fromDO(Heroe heroe) {
    return HeroeEntity
        .builder()
        .name(heroe.getName())
        .realName(heroe.getRealName())
        .build();
  }

  
}
