package com.example.heroes.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Defines a {@link RuntimeException} that is thrown when a heroe couldn't be found.
 */
@AllArgsConstructor
@Getter
public class HeroeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final int id;
}
