package com.example.heroes;

import com.example.heroes.config.AppConfiguration;
import org.springframework.boot.SpringApplication;

public class Application {

  public static void main(String[] args) {
    SpringApplication.run(AppConfiguration.class, args);  
  }

}
