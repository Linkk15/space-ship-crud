package spaceships.crud.domain.exceptions;

public class SpaceShipInternalServerException extends RuntimeException {
  public SpaceShipInternalServerException(String message) {
    super(message);
  }
}
