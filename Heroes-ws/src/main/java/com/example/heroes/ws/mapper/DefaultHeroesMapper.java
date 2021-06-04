package com.example.heroes.ws.mapper;

import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.ws.dto.HeroeDTO;
import org.springframework.stereotype.Component;

@Component
public class DefaultHeroesMapper implements HeroesMapper {

  @Override
  public HeroeDTO toDTO(Heroe domainObject) {
    return HeroeDTO
        .builder()
        .id(domainObject.getId())
        .name(domainObject.getName())
        .build();
  }

  @Override
  public Heroe fromDTO(HeroeDTO dto) {
    return Heroe
        .builder()
        .id(dto.getId())
        .name(dto.getName())
        .build();
  }

}
