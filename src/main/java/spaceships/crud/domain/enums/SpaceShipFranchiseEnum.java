package spaceships.crud.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SpaceShipFranchiseEnum {
  STAR_WARS("STAR WARS"),

  DUNNE("DUNNE"),

  STAR_TREK("STAR TREK");

  private final String franchise;

  public static SpaceShipFranchiseEnum fromString(String franchise) {
    for (SpaceShipFranchiseEnum spaceShipFranchise : SpaceShipFranchiseEnum.values()) {
      if (spaceShipFranchise.getFranchise().equalsIgnoreCase(franchise)) {
        return spaceShipFranchise;
      }
    }
    throw new IllegalArgumentException("Invalid franchise: " + franchise);
  }
}
