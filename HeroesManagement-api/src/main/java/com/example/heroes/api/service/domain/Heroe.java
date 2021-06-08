package com.example.heroes.api.service.domain;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "Builder")
@Data
public class Heroe {

  int id;

  String name;

  String realName;

}
