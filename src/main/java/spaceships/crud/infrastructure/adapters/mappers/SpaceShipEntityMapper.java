package spaceships.crud.infrastructure.adapters.mappers;

import org.mapstruct.*;
import spaceships.crud.domain.entities.SpaceShipEntity;
import spaceships.crud.domain.models.SpaceShip;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpaceShipEntityMapper {
  SpaceShipEntity toEntity(SpaceShip spaceShip);

  SpaceShip toDto(SpaceShipEntity spaceShipEntity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  SpaceShipEntity partialUpdate(
      SpaceShip spaceShip, @MappingTarget SpaceShipEntity spaceShipEntity);
}
