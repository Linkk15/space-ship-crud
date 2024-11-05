package spaceships.crud.domain.exceptions;

public class SpaceShipNotFoundException extends RuntimeException {

  public SpaceShipNotFoundException(Long id) {
    super(String.format("SpaceShip %s not found.", id));
  }
}
