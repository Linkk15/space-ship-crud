package spaceships.crud.domain.models;

import lombok.Builder;
import spaceships.crud.domain.enums.SpaceShipErrorLevelEnum;

@Builder
public record SpaceShipError(String code, String message, SpaceShipErrorLevelEnum level, String description) {}
