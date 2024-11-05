package spaceships.crud.domain.entities;

import java.time.LocalDate;
import lombok.*;
import org.springframework.data.annotation.Id;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;

@Builder
@Data
public class SpaceShipEntity {
  @Id private Long id;

  private String name;
  private SpaceShipFranchiseEnum franchise;
  private LocalDate dateRelease;
}
