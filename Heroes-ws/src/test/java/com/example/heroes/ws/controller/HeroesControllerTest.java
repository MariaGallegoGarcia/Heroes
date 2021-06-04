package com.example.heroes.ws.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.heroes.api.service.HeroesService;
import com.example.heroes.api.service.domain.Heroe;
import com.example.heroes.ws.dto.HeroeDTO;
import com.example.heroes.ws.mapper.HeroesMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public class HeroesControllerTest {

  private static final int IRON_MAN_ID = 1;

  private static final Heroe JESSICA_JONES = Heroe
      .builder()
      .id(42)
      .name("Jessica Jones")
      .build();
  private static final Heroe SCARLET_WITCH = Heroe
      .builder()
      .id(43)
      .name("Scarlet Witch")
      .build();
  private static final HeroeDTO JESSICA_JONES_DTO = HeroeDTO
      .builder()
      .id(JESSICA_JONES.getId())
      .name(JESSICA_JONES.getName())
      .build();
  private static final HeroeDTO SCARLET_WITCH_DTO = HeroeDTO
      .builder()
      .id(SCARLET_WITCH.getId())
      .name(SCARLET_WITCH.getName())
      .build();

  private HeroesController controller;

  private HeroesService service = mock(DefaultHeroesService.class);

  private HeroesMapper mapper = mock(HeroesMapper.class);

  private WebTestClient client;

  @BeforeEach
  public void init() {
    this.controller = new HeroesController(this.service, this.mapper);
    client = WebTestClient.bindToController(controller).build();
    when(mapper.toDTO(SCARLET_WITCH)).thenReturn(SCARLET_WITCH_DTO);
    when(mapper.fromDTO(SCARLET_WITCH_DTO)).thenReturn(SCARLET_WITCH);
    when(mapper.toDTO(JESSICA_JONES)).thenReturn(JESSICA_JONES_DTO);
    when(mapper.fromDTO(JESSICA_JONES_DTO)).thenReturn(JESSICA_JONES);
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
  public void deletesHeroeById() {
    when(service.delete(IRON_MAN_ID)).thenReturn(Mono.empty());

    client
        .delete()
        .uri(HeroesController.PATH + "/" + IRON_MAN_ID)
        .exchange()
        .expectStatus()
        .isOk();
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
}
