package spaceships.crud.application.usecases;

import reactor.core.publisher.Mono;
import spaceships.crud.domain.models.SpaceShip;

public interface SaveSpaceShipUseCase {
  Mono<SpaceShip> postSpaceShip(SpaceShip spaceShip);
}
