package com.example.heroes.ws.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.heroes.api.service.HeroesService;
import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.ws.dto.HeroeRequestDTO;
import com.example.heroes.ws.dto.HeroeResponseDTO;
import com.example.heroes.ws.mapper.HeroesRequestMapper;
import com.example.heroes.ws.mapper.HeroesResponseMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class HeroesControllerTest {

  private static final int IRON_MAN_ID = 1;

  private static final Heroe JESSICA_JONES = Heroe
      .builder()
      .id(42)
      .name("Jessica Jones")
      .realName("Jessica Jones")
      .build();
  private static final Heroe SCARLET_WITCH = Heroe
      .builder()
      .id(43)
      .name("Scarlet Witch")
      .realName("Wanda Maximoff")
      .build();
  private static final HeroeResponseDTO JESSICA_JONES_DTO = HeroeResponseDTO
      .builder()
      .id(JESSICA_JONES.getId())
      .name(JESSICA_JONES.getName())
      .realName(JESSICA_JONES.getRealName())
      .build();
  private static final HeroeResponseDTO SCARLET_WITCH_DTO = HeroeResponseDTO
      .builder()
      .id(SCARLET_WITCH.getId())
      .name(SCARLET_WITCH.getName())
      .realName(SCARLET_WITCH.getRealName())
      .build();
  private static final HeroeRequestDTO JESSICA_JONES_REQUEST_DTO = HeroeRequestDTO
      .builder()
      .name(JESSICA_JONES.getName())
      .realName(JESSICA_JONES.getRealName())
      .build();
  private static final HeroeRequestDTO SCARLET_WITCH_REQUEST_DTO = HeroeRequestDTO
      .builder()
      .name(SCARLET_WITCH.getName())
      .realName(SCARLET_WITCH.getRealName())
      .build();

  private HeroesService service = mock(HeroesService.class);

  private HeroesRequestMapper requestMapper = mock(HeroesRequestMapper.class);

  private HeroesResponseMapper responseMapper = mock(HeroesResponseMapper.class);

  @InjectMocks
  private HeroesController controller;

  @BeforeEach
  public void init() {
    when(responseMapper.toDTO(SCARLET_WITCH)).thenReturn(SCARLET_WITCH_DTO);
    when(requestMapper.fromDTO(SCARLET_WITCH_REQUEST_DTO)).thenReturn(SCARLET_WITCH);
    when(responseMapper.toDTO(JESSICA_JONES)).thenReturn(JESSICA_JONES_DTO);
    when(requestMapper.fromDTO(JESSICA_JONES_REQUEST_DTO)).thenReturn(JESSICA_JONES);
  }

  @Test
  public void deletesHeroe() {
    when(service.delete(IRON_MAN_ID)).thenReturn(Mono.empty());

    controller.delete(IRON_MAN_ID)
        .as(StepVerifier::create)
        .expectComplete()
        .verify();
    verify(service, times(1)).delete(IRON_MAN_ID);
  }

  @Test
  public void getAllHeroes() {
    when(service.getAll()).thenReturn(Flux.fromIterable(List.of(JESSICA_JONES, SCARLET_WITCH)));

    controller.getHeroes()
        .as(StepVerifier::create)
        .expectNext(ResponseEntity.of(Optional.of(JESSICA_JONES_DTO)))
        .expectNext(ResponseEntity.of(Optional.of(SCARLET_WITCH_DTO)))
        .expectComplete()
        .verify();
  }

  @Test
  public void getAllByPartialName() {
    when(service.getAllByPartialName("Jones"))
        .thenReturn(Flux.fromIterable(List.of(JESSICA_JONES)));

    controller.getAllByPartialName("Jones")
        .as(StepVerifier::create)
        .expectNext(ResponseEntity.of(Optional.of(JESSICA_JONES_DTO)))
        .expectComplete()
        .verify();
  }

  @Test
  public void getHeroeById() {
    when(service.getById(JESSICA_JONES.getId())).thenReturn(Mono.just(JESSICA_JONES));

    controller.getHeroeById(JESSICA_JONES_DTO.getId())
        .as(StepVerifier::create)
        .expectNext(ResponseEntity.of(Optional.of(JESSICA_JONES_DTO)))
        .expectComplete()
        .verify();
  }

  @Test
  public void saveHeroe() {
    when(service.save(JESSICA_JONES)).thenReturn(Mono.empty());

    controller.save(JESSICA_JONES_REQUEST_DTO)
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  public void updateHeroe() {
    when(service.update(JESSICA_JONES)).thenReturn(Mono.empty());

    controller.update(JESSICA_JONES_REQUEST_DTO)
        .as(StepVerifier::create)
        .verifyComplete();
  }
}
