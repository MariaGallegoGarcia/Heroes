package com.example.heroes;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.example.heroes.config.AppConfiguration;

@Configuration
@Import({AppConfiguration.class})
public class AppTestConfiguration {

}
