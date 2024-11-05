package spaceships.crud.infrastructure.adapters.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "kafka-enabled", havingValue = "true")
public class SpaceShipProducer {

  @Value("${spring.cloud.stream.bindings.spaceShipProducer-out-0.destination}")
  private final String topic;

  private final StreamBridge streamBridge;

  @Bean
  public void spaceShipProducer() {
    streamBridge.send(topic, "Hello from producer");
  }
}
