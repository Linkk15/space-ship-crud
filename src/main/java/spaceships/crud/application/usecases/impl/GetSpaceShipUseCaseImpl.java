package spaceships.crud.application.usecases.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spaceships.crud.application.usecases.GetSpaceShipUseCase;
import spaceships.crud.domain.enums.SpaceShipFranchiseEnum;
import spaceships.crud.domain.exceptions.SpaceShipDataBaseErrorException;
import spaceships.crud.domain.exceptions.SpaceShipInternalServerException;
import spaceships.crud.domain.models.SpaceShip;
import spaceships.crud.domain.repositories.SpaceShipRepository;
import spaceships.crud.infrastructure.adapters.mappers.SpaceShipEntityMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetSpaceShipUseCaseImpl implements GetSpaceShipUseCase {

  private final SpaceShipRepository spaceShipRepository;
  private final SpaceShipEntityMapper spaceShipEntityMapper;

  @Override
  public Mono<SpaceShip> getSpaceShipById(Long id) {
    return spaceShipRepository
        .findById(id)
        .map(spaceShipEntityMapper::toDto)
        .doOnError(error -> log.error("Error retrieving spaceship by id {}", id, error))
        .onErrorMap(
            error ->
                new SpaceShipInternalServerException(
                    String.format("Error retrieving spaceship by id %s", error)));
  }

  @Override
  public Flux<SpaceShip> getAllSpaceShips(Integer pageNo, Integer pageSize) {
    return spaceShipRepository
        .findAllBy(PageRequest.of(pageNo, pageSize))
        .map(spaceShipEntityMapper::toDto)
        .doOnError(error -> log.error("Error retrieving all spaceships", error))
        .onErrorMap(
            error ->
                new SpaceShipInternalServerException(
                    String.format("Error retrieving spaceship by id %s", error)));
  }

  @Override
  public Flux<SpaceShip> getAllSpaceShipsByName(String name) {
    return spaceShipRepository
        .findAllByNameContainingIgnoreCase(name)
        .map(spaceShipEntityMapper::toDto)
        .doOnError(error -> log.error("Error retrieving all spaceships by name", error))
        .onErrorMap(
            error ->
                new SpaceShipDataBaseErrorException(
                    String.format("Error retrieving spaceship by name %s", name)));
  }

  @Override
  public Flux<SpaceShip> getSpaceShipsByFranchise(String franchise) {
    return spaceShipRepository
        .findByFranchise(SpaceShipFranchiseEnum.fromString(franchise))
        .map(spaceShipEntityMapper::toDto)
        .doOnError(
            error -> log.error("Error retrieving spaceships by franchise {}", franchise, error))
        .onErrorMap(
            error ->
                new SpaceShipDataBaseErrorException(
                    String.format("Error retrieving spaceship by id %s", error)));
  }

  @Aspect
  @Slf4j
  @Component
  static final class GetSpaceShipAspect {

    private static final String NEGATIVE_ID_POINT_CUT =
        "execution(* spaceships.crud.application.usecases.impl.GetSpaceShipUseCaseImpl.getSpaceShipById(..)) && args(id)";
    private static final String SUCCESS_TRANSACTION_POINT_CUT =
        "execution(* spaceships.crud.application.usecases.impl.GetSpaceShipUseCaseImpl..*(..))";

    @Before(NEGATIVE_ID_POINT_CUT)
    public void negativeIdAspectLog(JoinPoint joinPoint, Long id) {
      Optional.ofNullable(id)
          .filter(idValue -> idValue < 0)
          .ifPresent(
              negativeId -> log.warn("Trying to find a SpaceShip with negative id! -> {}", id));
    }

    @After(SUCCESS_TRANSACTION_POINT_CUT)
    public void completeTransaction(JoinPoint joinPoint) {
      log.info("Transaction completed successfully !");
    }

    /*
    We can use annotations like @AfterReturning and @AfterThrowing to add some business logic after the execution of the business method.
    Also, @Around advice can be used to control the execution flow.
     */
  }
}
