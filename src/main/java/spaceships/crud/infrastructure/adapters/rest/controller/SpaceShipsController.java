package spaceships.crud.infrastructure.adapters.rest.controller;

import static org.springframework.http.HttpStatus.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spaceships.crud.application.usecases.EraseSpaceShipUseCase;
import spaceships.crud.application.usecases.GetSpaceShipUseCase;
import spaceships.crud.application.usecases.ModifySpaceShipUseCase;
import spaceships.crud.application.usecases.SaveSpaceShipUseCase;
import spaceships.crud.domain.models.SpaceShip;

@RestController
@RequestMapping("/spaceships")
@Validated
@RequiredArgsConstructor
@Slf4j
public class SpaceShipsController {

  private final GetSpaceShipUseCase getSpaceShipUseCase;
  private final SaveSpaceShipUseCase saveSpaceShipUseCase;
  private final EraseSpaceShipUseCase eraseSpaceShipUseCase;
  private final ModifySpaceShipUseCase modifySpaceShipUseCase;

  // @PreAuthorize("hasAnyAuthority('" + Authorities.CANALES_ADD + "')")
  @GetMapping(value = "/spaceship/{id}")
  @ResponseStatus(OK)
  public Mono<SpaceShip> getSpaceShip(@PathVariable Long id) {
    log.info("Getting spaceship by id {}", id);

    return getSpaceShipUseCase.getSpaceShipById(id).switchIfEmpty(Mono.empty());
  }

  @GetMapping(value = "/all")
  @ResponseStatus(OK)
  public Flux<SpaceShip> getAllSpaceShips(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    log.info("Getting all spaceships");

    return getSpaceShipUseCase.getAllSpaceShips(pageNo, pageSize).switchIfEmpty(Flux.empty());
  }

  @GetMapping(value = "/all/byfranchise")
  @ResponseStatus(OK)
  public Flux<SpaceShip> getSpaceShipsByFranchise(@Valid @RequestParam String franchise) {
    log.info("Getting spaceships by franchise {}", franchise);

    return getSpaceShipUseCase.getSpaceShipsByFranchise(franchise).switchIfEmpty(Flux.empty());
  }

  @GetMapping(value = "/all/byname")
  @ResponseStatus(OK)
  public Flux<SpaceShip> getSpaceShipsByName(@Valid @RequestParam String name) {
    log.info("Getting spaceships by name {}", name);

    return getSpaceShipUseCase.getAllSpaceShipsByName(name).switchIfEmpty(Flux.empty());
  }

  @PostMapping(value = "/new")
  public Mono<ResponseEntity<SpaceShip>> createSpaceShip(@Valid @RequestBody SpaceShip spaceShip) {
    log.info("Creating spaceship {}", spaceShip);

    return saveSpaceShipUseCase
        .postSpaceShip(spaceShip)
        .map(
            createdSpaceShip -> {
              final var uri =
                  ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}")
                      .buildAndExpand(createdSpaceShip.id())
                      .toUri();
              return ResponseEntity.created(uri).body(createdSpaceShip);
            })
        .onErrorReturn(ResponseEntity.badRequest().body(spaceShip));
  }

  @DeleteMapping(value = "/spaceship/{id}")
  @ResponseStatus(NO_CONTENT)
  public void eraseSpaceShip(@PathVariable Long id) {
    log.info("Deleting spaceship by id {}", id);

    eraseSpaceShipUseCase.eraseSpaceShip(id);
  }

  @PutMapping(value = "/spaceship/{id}")
  @ResponseStatus(ACCEPTED)
  public void putSpaceShip(@PathVariable Long id, @Valid @RequestBody SpaceShip spaceShip) {
    log.info("Updating spaceship by id {}", id);

    modifySpaceShipUseCase.putSpaceShip(id, spaceShip);
  }
}
