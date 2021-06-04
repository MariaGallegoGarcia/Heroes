package com.example.heroes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder(builderClassName = "Builder")
@Table
@AllArgsConstructor
@Value
public class HeroeEntity {

  @Id
  int id;

  String name;
}
