package spaceships.crud.domain.exceptions;

public class SpaceShipDataBaseErrorException extends RuntimeException {
  public SpaceShipDataBaseErrorException(String message) {
    super(message);
  }
}
