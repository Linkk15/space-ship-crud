package spaceships.crud.application.usecases.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spaceships.crud.application.usecases.ModifySpaceShipUseCase;
import spaceships.crud.domain.entities.SpaceShipEntity;
import spaceships.crud.domain.exceptions.SpaceShipNotFoundException;
import spaceships.crud.domain.models.SpaceShip;
import spaceships.crud.domain.repositories.SpaceShipRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifySpaceShipUseCaseImpl implements ModifySpaceShipUseCase {

  private final SpaceShipRepository spaceShipRepository;

  @Override
  @Cacheable(value = "spaceships", key = "#id")
  public void putSpaceShip(Long id, SpaceShip spaceShip) {
    spaceShipRepository
        .findById(id)
        .doOnSuccess(
            spaceShipEntity ->
                updateSpaceShip.accept(spaceShipEntity, spaceShip, spaceShipRepository))
        .doOnError(error -> log.error("Error retrieving spaceship by id {}", id, error))
        .onErrorMap(error -> new SpaceShipNotFoundException(id))
        .subscribe();
  }

  private final TriConsumer<SpaceShipEntity, SpaceShip, SpaceShipRepository> updateSpaceShip =
      (spaceShipEntity, spaceShip, repository) -> {
        // also we can make the put with BeanUtils.copyProperties(spaceShipEntity, spaceShip);
        Optional.ofNullable(spaceShip.name()).ifPresent(spaceShipEntity::setName);
        Optional.ofNullable(spaceShip.franchise()).ifPresent(spaceShipEntity::setFranchise);
        Optional.ofNullable(spaceShip.dateRelease()).ifPresent(spaceShipEntity::setDateRelease);
        repository.save(spaceShipEntity).subscribe();
      };
}
