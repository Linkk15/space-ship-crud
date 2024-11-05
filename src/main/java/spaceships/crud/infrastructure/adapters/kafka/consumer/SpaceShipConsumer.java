package spaceships.crud.infrastructure.adapters.kafka.consumer;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import spaceships.crud.application.usecases.SaveSpaceShipUseCase;
import spaceships.crud.domain.models.SpaceShip;

@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "kafka-enabled", havingValue = "true")
public class SpaceShipConsumer {

  private final SaveSpaceShipUseCase saveSpaceShipUseCase;

  @Bean
  public Consumer<Message<SpaceShip>> spaceShipConsumer() {
    return message -> {
      log.info("Message received: {}", message.getPayload());
      saveSpaceShipUseCase.postSpaceShip(message.getPayload());
    };
  }
}
