package spaceships.crud.domain.exceptions;

public class SpaceShipExistsException extends RuntimeException {
  public SpaceShipExistsException(Long id) {
    super(String.format("SpaceShip %s already exists.", id));
  }
}
