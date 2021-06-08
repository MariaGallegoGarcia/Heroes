package com.example.heroes.ws.mapper;

import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.ws.dto.HeroeRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class HeroesRequestMapper {

  public Heroe fromDTO(HeroeRequestDTO dto) {
    return Heroe
        .builder()
        .name(dto.getName())
        .realName(dto.getRealName())
        .build();
  }

}
