package spaceships.crud.application.usecases.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceships.crud.application.usecases.EraseSpaceShipUseCase;
import spaceships.crud.domain.exceptions.SpaceShipNotFoundException;
import spaceships.crud.domain.repositories.SpaceShipRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class EraseSpaceShipUseCaseImpl implements EraseSpaceShipUseCase {

  private final SpaceShipRepository spaceShipRepository;

  @Override
  public void eraseSpaceShip(Long id) {
    spaceShipRepository
        .deleteById(id)
        .doOnError(error -> log.error("Error deleting spaceship by id {}", id, error))
        .onErrorMap(error -> new SpaceShipNotFoundException(id))
        .subscribe();
  }
}
