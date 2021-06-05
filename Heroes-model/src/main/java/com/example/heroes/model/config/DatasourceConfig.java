package com.example.heroes.model.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryOptions.Builder;
import org.h2.util.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;


@Configuration
public class DatasourceConfig {

  @Bean
  public ConnectionFactory connectionFactory(R2DBCConfigurationProperties properties) {
    ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(properties.getUrl());
    Builder optionBuilder = ConnectionFactoryOptions.builder().from(baseOptions);
    if (!StringUtils.isNullOrEmpty(properties.getUser())) {
      optionBuilder = optionBuilder.option(ConnectionFactoryOptions.USER, properties.getUser());
    }

    if (!StringUtils.isNullOrEmpty(properties.getPassword())) {
      optionBuilder =
          optionBuilder.option(ConnectionFactoryOptions.PASSWORD, properties.getPassword());
    }

    ConnectionFactory connectionFactory = ConnectionFactories.get(optionBuilder.build());
    return connectionFactory;
  }


  @Bean
  public CommandLineRunner initDatabase(ConnectionFactory connectionFactory) {
    return (args) -> Flux.from(connectionFactory.create())
        .flatMap(c -> Flux
            .from(c.createBatch().add("drop table if exists Heroe")
                .add("create table IF NOT EXISTS Heroe" + "(id SERIAL PRIMARY KEY,"
                    + " name varchar (255) not null)")
                .add("insert into Heroe(id,name)" + "values(1, 'Aquaman')")
                .add("insert into Heroe(id,name)" + "values(2, 'Iron man')")
                .add("insert into Heroe(id,name)" + "values(3, 'Súper López')").execute())
            .doFinally((st) -> c.close()))
        .log().blockLast();
  }
}
