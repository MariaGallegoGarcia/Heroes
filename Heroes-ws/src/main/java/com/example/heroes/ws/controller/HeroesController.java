package com.example.heroes.ws.controller;

import com.example.heroes.api.service.HeroesService;
import com.example.heroes.exceptions.HeroeNotFoundException;
import com.example.heroes.ws.dto.HeroeDTO;
import com.example.heroes.ws.mapper.HeroesMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequiredArgsConstructor
@Slf4j
public class HeroesController {

  public static final String PATH = "/heroes";

  private HeroesService heroesService;

  private HeroesMapper mapper;

  /**
   * Returns all the heroes.
   */
  @GetMapping
  @ApiOperation("Returns all the heroes")
  @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK", response = HeroeDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroes not found")})
  @ResponseBody
  public Flux<ResponseEntity<HeroeDTO>> getHeroes() {
    return this.heroesService
        .getAll()
        .map(mapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
  }

  /**
   * Inserts or updates a heroe.
   *
   * @return Response code
   */
  @PutMapping
  @ApiOperation("Inserts or updates a heroe")
  @ApiResponses(value = {@ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")})
  public Mono<Void> upsert(@RequestBody HeroeDTO heroesDto) {
    return this.heroesService.upsert(mapper.fromDTO(heroesDto));
  }

  /**
   * Deletes a heroe by ID.
   *
   * @return Response code
   */
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
  @GetMapping("/{id}")
  @ApiOperation("Gets a heroe by ID")
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroe not found")})
  public Mono<ResponseEntity<HeroeDTO>> getHeroeById(@PathVariable("id") int id) {
    return this.heroesService.getById(id)
        .map(mapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.error(new HeroeNotFoundException(id)));
  }

  /**
   * Searches all the heroes that contains this string on the name.
   */
  @GetMapping("/name/{name}")
  @ApiResponses(value = {
      @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK", response = HeroeDTO.class),
      @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Heroes not found")})
  @ResponseBody
  public Flux<ResponseEntity<HeroeDTO>> getAllByPartialName(String name) {
    return this.heroesService.getAllByPartialName(name)
        .map(mapper::toDTO)
        .map(heroe -> new ResponseEntity<>(heroe, HttpStatus.OK))
        .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
  }

  @ExceptionHandler(HeroeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleInvalidIdProvided(final HeroeNotFoundException ex) {
    log.warn("Heroe with id {} couldn't be found.", ex.getId());
  }
}
