package com.example.heroes.config;

import com.example.heroes.model.config.R2DBCConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.example.heroes")
@EnableConfigurationProperties(R2DBCConfigurationProperties.class)
public class AppConfiguration {

}
