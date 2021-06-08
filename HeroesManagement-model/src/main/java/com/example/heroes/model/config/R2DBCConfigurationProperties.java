package com.example.heroes.model.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "r2dbc")
@Getter
@Setter
public class R2DBCConfigurationProperties {

  private String url;

  private String user;

  private String password;

}
