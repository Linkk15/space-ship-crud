package spaceships.crud.application.usecases.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spaceships.crud.domain.entities.SpaceShipEntity;
import spaceships.crud.domain.exceptions.SpaceShipInternalServerException;
import spaceships.crud.domain.repositories.SpaceShipRepository;
import spaceships.crud.infrastructure.adapters.mappers.SpaceShipEntityMapper;

@ExtendWith(MockitoExtension.class)
class GetSpaceShipUseCaseImplTest {

  @Mock private SpaceShipRepository spaceShipRepository;
  @Mock private SpaceShipEntityMapper spaceShipEntityMapper;
  @InjectMocks private GetSpaceShipUseCaseImpl sut;

  @Test
  @DisplayName("Should return a valid SpaceShip by id")
  void test1() {
    when(spaceShipRepository.findById(anyLong()))
        .thenReturn(Mono.just(SpaceShipEntity.builder().build()));

    assertDoesNotThrow(() -> sut.getSpaceShipById(1L));

    verify(spaceShipRepository, times(1)).findById(anyLong());
  }

  @Test
  @DisplayName("Should return all SpaceShips")
  void test2() {
    when(spaceShipRepository.findAllBy(any(Pageable.class)))
        .thenReturn(Flux.just(SpaceShipEntity.builder().build()));

    assertDoesNotThrow(() -> sut.getAllSpaceShips(anyInt(), 1));

    verify(spaceShipRepository, times(1)).findAllBy(any(Pageable.class));
  }

  @Test
  @DisplayName("Should throw SpaceShipDataBaseErrorException when repository throws any exception")
  void test3() {
    when(spaceShipRepository.findAllByNameContainingIgnoreCase(anyString()))
        .thenThrow(SpaceShipInternalServerException.class);

    assertThatThrownBy(() -> sut.getAllSpaceShipsByName(anyString()))
        .isInstanceOf(RuntimeException.class);

    verify(spaceShipRepository, times(1)).findAllByNameContainingIgnoreCase(anyString());
    verifyNoInteractions(spaceShipEntityMapper);
  }
}
