package spaceships.crud.application.usecases.impl;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;
import spaceships.crud.domain.models.SpaceShip;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
class SaveSpaceShipUseCaseImplTest {

  @LocalServerPort int port;
  @Autowired TestRestTemplate testRestTemplate;

  // @Autowired MockMvc mockMvc;

  @ParameterizedTest
  @EnumSource(SpaceShipFranchiseEnum.class)
  @DisplayName("Should save a SpaceShip in BBDD correctly")
  void test1(SpaceShipFranchiseEnum spaceShipFranchiseEnum) {
    // given
    final var spaceShip =
        SpaceShip.builder()
            .id(1L)
            .name("X-Wing")
            .dateRelease(LocalDate.now())
            .franchise(spaceShipFranchiseEnum)
            .build();

    // when
    final var postController =
        testRestTemplate
            .withBasicAuth("admin", "admin")
            .postForEntity("/spaceships/new", spaceShip, SpaceShip.class);

    // then
    assertThat(postController.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  //  @Test
  //  @DisplayName("Should return Bad Request when send wrong SpaceShip")
  //  void test2() throws Exception {
  //    // given
  //    final var spaceShipJson =
  //        "{\"name\":\"Test bad
  // request\",\"franchise\":\"TestT\",\"dateRelease\":\"2024-10-30\"}";
  //
  //    // when - then
  //    mockMvc
  //        .perform(
  //
  // post("/spaceships/new").contentType(MediaType.APPLICATION_JSON).content(spaceShipJson))
  //        .andExpect(status().isBadRequest());
  //  }

  @Test
  @DisplayName("Should return Bad Request when send wrong SpaceShip")
  void test2() {
    // given
    final var requestSpecefication =
        new RequestSpecBuilder()
            .setPort(port)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    final var spaceShipJson =
        "{\"name\":\"Test bad request\",\"franchise\":\"DUNNE\",\"dateRelease\":\"2024-10-30eeqw\"}";

    // when - then
    given(requestSpecefication)
        .when()
        .body(spaceShipJson)
        .auth()
        .basic("admin", "admin")
        .post("/spaceships/new")
        .then()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .log()
        .ifValidationFails(LogDetail.ALL);
  }
}
