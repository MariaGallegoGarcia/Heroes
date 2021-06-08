package com.example.heroes.config;

import com.example.heroes.model.config.R2DBCConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.example.heroes")
@EnableConfigurationProperties(R2DBCConfigurationProperties.class)
@EnableR2dbcRepositories(basePackages = "com.example.heroes.model")
public class AppConfiguration {

}
