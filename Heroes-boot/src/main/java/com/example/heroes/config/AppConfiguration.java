package com.example.heroes.config;

import com.example.heroes.model.entity.HeroeEntity;
import com.example.heroes.model.repository.HeroesRepository;
import java.util.stream.Stream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@SpringBootApplication(scanBasePackages = "com.example.heroes")
public class AppConfiguration {

  @Bean
  ApplicationRunner init(HeroesRepository repository, DatabaseClient client) {
    return args -> {
      client.sql("create table IF NOT EXISTS Heroe"
          + "(id SERIAL PRIMARY KEY,"
          + " text varchar (255) not null,"
          + " completed boolean"
          + " default false);")
          .fetch()
          .first()
          .subscribe();
      client.sql("DELETE FROM Heroe;").fetch().first().subscribe();

      Stream<HeroeEntity> stream = Stream.of(
          HeroeEntity
              .builder()
              .id(1)
              .name("Aquaman")
              .build(),
          HeroeEntity
              .builder()
              .id(2)
              .name("Iron man")
              .build(),
          HeroeEntity
              .builder()
              .id(3)
              .name("Súper López")
              .build());

      // initialize the database

      repository.saveAll(Flux.fromStream(stream)).then().subscribe(); // execute

    };
  }

}
