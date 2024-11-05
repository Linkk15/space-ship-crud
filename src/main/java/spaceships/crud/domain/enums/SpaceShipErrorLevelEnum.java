package spaceships.crud.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpaceShipErrorLevelEnum {
  ERROR("ERROR"),
  WARNING("WARNING"),
  CRITICAL("CRITICAL");

  private final String value;
}
