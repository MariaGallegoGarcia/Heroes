package com.example.heroes.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder(builderClassName = "Builder")
@Table("Heroe")
@Data
public class HeroeEntity {

  @Id
  int id;

  @NonNull
  String name;

  @Column("realName")
  String realName;

}
