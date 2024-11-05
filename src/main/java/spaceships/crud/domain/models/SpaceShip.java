package spaceships.crud.domain.models;

import java.time.LocalDate;
import lombok.Builder;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;

@Builder
public record SpaceShip(
    Long id, String name, SpaceShipFranchiseEnum franchise, LocalDate dateRelease) {}
