package com.example.heroes.api.service.domain;

import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
public class Heroe {

  int id;

  String name;
}
