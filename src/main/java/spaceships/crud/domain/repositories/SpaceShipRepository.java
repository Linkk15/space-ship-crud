package spaceships.crud.domain.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import spaceships.crud.domain.entities.SpaceShipEntity;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;

public interface SpaceShipRepository extends R2dbcRepository<SpaceShipEntity, Long> {
  Flux<SpaceShipEntity> findByFranchise(SpaceShipFranchiseEnum franchise);

  Flux<SpaceShipEntity> findAllByNameContainingIgnoreCase(String name);

  Flux<SpaceShipEntity> findAllBy(Pageable pageable);
}
