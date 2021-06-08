package com.example.heroes.ws.controller;

import com.example.heroes.annotation.Timed;
import com.example.heroes.api.service.HeroesService;
import com.example.heroes.exceptions.HeroeNotFoundException;
import com.example.heroes.ws.dto.HeroeRequestDTO;
import com.example.heroes.ws.dto.HeroeResponseDTO;
import com.example.heroes.ws.mapper.HeroesRequestMapper;
import com.example.heroes.ws.mapper.HeroesResponseMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = HeroesController.PATH)
@AllArgsConstructor
@Slf4j
public class HeroesController {

  public static final String PATH = "/heroes";

  private HeroesService heroesService;

  private HeroesRequestMapper requestMapper;

  private HeroesResponseMapper responseMapper;

  /**
   * Returns all the heroes.
   */
  @Timed(millis = 1000)
  @GetMapping
  @ApiOperation("Returns all the heroes")
  @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK", response = HeroeResponseDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroes not found")})
  @ResponseBody
  public Flux<ResponseEntity<HeroeResponseDTO>> getHeroes() {
    return this.heroesService
        .getAll()
        .map(responseMapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
  }

  /**
   * Updates a heroe.
   *
   * @return Response code
   */
  @Timed(millis = 1000)
  @PutMapping
  @ApiOperation("Update a heroe")
  @ApiResponses(value = {@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")})
  public Mono<Void> update(@RequestBody HeroeRequestDTO heroesDto) {
    return this.heroesService.update(requestMapper.fromDTO(heroesDto));
  }

  /**
   * Saves a heroe.
   *
   * @return Response code
   */
  @Timed(millis = 1000)
  @PostMapping
  @ApiOperation("Save a heroe")
  @ApiResponses(value = {@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")})
  public Mono<Void> save(@RequestBody HeroeRequestDTO heroesDto) {
    return this.heroesService.save(requestMapper.fromDTO(heroesDto));
  }

  /**
   * Deletes a heroe by ID.
   *
   * @return Response code
   */
  @Timed(millis = 1000)
  @DeleteMapping("/{id}")
  @ApiOperation("Deletes a heroe by ID")
  @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")})
  public Mono<Void> delete(@PathVariable("id") int id) {
    return this.heroesService.delete(id);
  }

  /**
   * Returns a heroe by ID.
   *
   * @return Response code
   */
  @Timed(millis = 1000)
  @GetMapping("/{id}")
  @ApiOperation("Gets a heroe by ID")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroe not found")})
  public Mono<ResponseEntity<HeroeResponseDTO>> getHeroeById(@PathVariable("id") int id) {
    return this.heroesService.getById(id)
        .map(responseMapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.error(new HeroeNotFoundException(id)));
  }

  /**
   * Searches all the heroes that contains this string on the name.
   */
  @Timed(millis = 1000)
  @GetMapping("/name/{partialName}")
  @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK", response = HeroeResponseDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroes not found")})
  @ResponseBody
  public Flux<ResponseEntity<HeroeResponseDTO>> getAllByPartialName(@PathVariable("partialName") String partialName) {
    return this.heroesService.getAllByPartialName(partialName)
        .map(responseMapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
  }

  @ExceptionHandler(HeroeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleInvalidIdProvided(final HeroeNotFoundException ex) {
    log.warn("Heroe with id {} couldn't be found.", ex.getId());
  }
}
