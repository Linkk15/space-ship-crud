package spaceships.crud;

import org.springframework.boot.SpringApplication;

public class TestSpaceshipscrudApplication {

  public static void main(String[] args) {
    SpringApplication.from(SpaceShipsCrudApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
