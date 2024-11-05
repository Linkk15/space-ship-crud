package spaceships.crud.application.usecases;

import spaceships.crud.domain.models.SpaceShip;

public interface ModifySpaceShipUseCase {

  void putSpaceShip(Long id, SpaceShip spaceShip);
}
