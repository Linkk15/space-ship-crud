package spaceships.crud.application.usecases.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import spaceships.crud.application.usecases.SaveSpaceShipUseCase;
import spaceships.crud.domain.models.SpaceShip;
import spaceships.crud.domain.repositories.SpaceShipRepository;
import spaceships.crud.infrastructure.adapters.mappers.SpaceShipEntityMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveSpaceShipUseCaseImpl implements SaveSpaceShipUseCase {

  private final SpaceShipRepository spaceShipRepository;
  private final SpaceShipEntityMapper spaceShipEntityMapper;

  @Override
  public Mono<SpaceShip> postSpaceShip(SpaceShip spaceShip) {
    return spaceShipRepository
        .save(spaceShipEntityMapper.toEntity(spaceShip))
        .map(spaceShipEntityMapper::toDto)
        .doOnError(throwable -> log.error("Error saving spaceship {}", throwable.getMessage()));
  }
}
