package spaceships.crud;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import spaceships.crud.domain.entities.SpaceShipEntity;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;
import spaceships.crud.domain.repositories.SpaceShipRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"spaceships.crud"})
@EnableR2dbcRepositories(basePackages = {"spaceships.crud.domain.repositories"})
public class SpaceShipsCrudApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpaceShipsCrudApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(SpaceShipRepository repository) {
    return args -> {
      for (int i = 0; i < 15; i++) {
        repository
            .save(
                SpaceShipEntity.builder()
                    .name("Millennium Falcon " + i)
                    .franchise(SpaceShipFranchiseEnum.STAR_WARS)
                    .dateRelease(LocalDate.now())
                    .build())
            .subscribe();
      }

      for (int j = 0; j < 10; j++) {
        repository
            .save(
                SpaceShipEntity.builder()
                    .name("Dunne spaceship " + j)
                    .franchise(SpaceShipFranchiseEnum.DUNNE)
                    .dateRelease(LocalDate.now())
                    .build())
            .subscribe();
      }

      repository
          .save(
              SpaceShipEntity.builder()
                  .name("Star Trek spaceship")
                  .franchise(SpaceShipFranchiseEnum.STAR_TREK)
                  .dateRelease(LocalDate.now())
                  .build())
          .subscribe();
    };
  }
}
