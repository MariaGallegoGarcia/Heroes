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
                .add("create table IF NOT EXISTS Heroe (id BIGINT NOT NULL AUTO_INCREMENT,"
                    + " name varchar (50) not null PRIMARY KEY, realName varchar (100))")
                .add("insert into Heroe(name, realName) values('Aquaman', 'Arthur Curry')")
                .add("insert into Heroe(name, realName) values('Iron man', 'Tony Stark')")
                .add("insert into Heroe(name, realName) values('Súper López', 'Juan López Fernández')")
                .execute())
            .doFinally((st) -> c.close()))
        .log().blockLast();
  }
}
