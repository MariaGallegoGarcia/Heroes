package com.example.heroes.api.service;

public interface GenericDOMapper<S, T> {

  /**
  * Converts given entity to DO.
  */
  S toDO(T domainObject);

  /**
  * Converts given DO to model.
  */
  T fromDO(S dataObject);
}