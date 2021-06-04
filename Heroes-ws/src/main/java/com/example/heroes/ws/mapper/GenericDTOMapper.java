package com.example.heroes.ws.mapper;

/**
* Defines a GenericMapper to transform entity objects into DTOs.
*
* @param <T> Domain object
* @param <DTO> DTO object
*/
public interface GenericDTOMapper<T, DTO> {

  /**
  * Converts given domain object to dto.
  */
  DTO toDTO(T domainObject);

  /**
  * Converts given dto to domain object.
  */
  T fromDTO(DTO dto);

}
