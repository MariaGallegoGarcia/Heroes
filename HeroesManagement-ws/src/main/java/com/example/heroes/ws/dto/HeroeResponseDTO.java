package com.example.heroes.ws.dto;

import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder(builderClassName = "Builder")
@Value
public class HeroeResponseDTO {

  int id;

  /**
   * The name of the heroe.
   */
  @NonNull
  @Pattern(regexp = "^[a-zA-Z0-9 .-]+$")
  String name;

  @Pattern(regexp = "^[a-zA-Z0-9 .-]+$")
  String realName;
}
