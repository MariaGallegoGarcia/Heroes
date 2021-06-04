package com.example.heroes.model.dao;

import com.example.heroes.model.entity.HeroeEntity;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class HeroesR2DBCDao implements HeroesDao {

  // ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb");

  private ConnectionFactory connectionFactory;

  @Override
  public Flux<HeroeEntity> getAllByPartialName(String partialName) {
    return Mono.from(connectionFactory.create())
        .flatMap((connection) -> Mono.from(connection
            .createStatement("SELECT * FROM Heroe WHERE name like '%$1%'")
            .bind("$1", partialName)
            .execute())
            .doFinally((st) -> close(connection)))
        .flatMapMany(result -> Flux.from(result.map((row, meta) ->
           new HeroeEntity(row.get("id", Integer.class), row.get("name", String.class)))));
  }

  private <T> Mono<T> close(Connection connection) {
    return Mono.from(connection.close())
      .then(Mono.empty());
  }
}
