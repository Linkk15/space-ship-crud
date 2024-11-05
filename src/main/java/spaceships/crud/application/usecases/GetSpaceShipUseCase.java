package spaceships.crud.application.usecases;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spaceships.crud.domain.models.SpaceShip;

public interface GetSpaceShipUseCase {
  Mono<SpaceShip> getSpaceShipById(Long id);

  Flux<SpaceShip> getAllSpaceShips(Integer pageNo, Integer pageSize);

  Flux<SpaceShip> getAllSpaceShipsByName(String name);

  Flux<SpaceShip> getSpaceShipsByFranchise(String franchise);
}
