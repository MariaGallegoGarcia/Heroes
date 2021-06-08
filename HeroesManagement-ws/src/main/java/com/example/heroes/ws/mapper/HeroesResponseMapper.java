package com.example.heroes.ws.mapper;

import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.ws.dto.HeroeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class HeroesResponseMapper {

  public HeroeResponseDTO toDTO(Heroe domainObject) {
    return HeroeResponseDTO
        .builder()
        .id(domainObject.getId())
        .name(domainObject.getName())
        .realName(domainObject.getRealName())
        .build();
  }

}
