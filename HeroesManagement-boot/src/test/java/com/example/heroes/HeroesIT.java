package com.example.heroes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.heroes.model.entity.HeroeEntity;
import com.example.heroes.ws.dto.HeroeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppTestConfiguration.class)
public class HeroesIT {

  private static final String PATH = "/heroes/";

  @LocalServerPort
  public int serverPort;

  @Autowired
  private WebTestClient client;

  @BeforeEach
  public void setUp() {
    client = client.mutate().responseTimeout(java.time.Duration.ofMillis(3000)).build();
  }

  @Test
  public void savesHeroe() {
    client
        .post()
        .uri(PATH)
        .bodyValue(getExampleHeroe())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  public void updatesHeroe() {
    client
        .post()
        .uri(PATH)
        .bodyValue(getExampleHeroe())
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  public void deletesHeroeByIdTest() {
    client
        .delete()
        .uri(PATH + "/" + 1)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  public void getHeroeById_whenIdNotFound() {
    client
        .get()
        .uri(PATH + "/" + 999)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void getsHeroeById() {
    final HeroeResponseDTO expectedHeroe = HeroeResponseDTO
        .builder()
        .id(2)
        .name("Iron man")
        .realName("Tony Stark")
        .build();
    final HeroeResponseDTO result = client
        .get()
        .uri(PATH + "/" + 2)
        .exchange()
        .expectStatus().isOk()
        .expectBody(HeroeResponseDTO.class)
        .returnResult()
        .getResponseBody();

    assertEquals(expectedHeroe, result);
  }

  private HeroeEntity getExampleHeroe() {
    return HeroeEntity
        .builder()
        .name("Wonder Woman")
        .realName("Diana Prince")
        .build();
  }
}
