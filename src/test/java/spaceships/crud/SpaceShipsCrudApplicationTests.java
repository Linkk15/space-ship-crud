package spaceships.crud;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpaceShipsCrudApplicationTests {

  @Autowired private SpaceShipsCrudApplication spaceShipsCrudApplication;

  @Test
  void contextLoads() {
    assertNotNull(spaceShipsCrudApplication);
  }
}
