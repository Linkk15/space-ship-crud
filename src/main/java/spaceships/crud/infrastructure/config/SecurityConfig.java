package spaceships.crud.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(
            exchanges ->
                exchanges
                    .pathMatchers(HttpMethod.GET, "/**")
                    .authenticated()
                    .pathMatchers(HttpMethod.POST, "/**")
                    .authenticated()
                    .pathMatchers(HttpMethod.PUT, "/**")
                    .authenticated()
                    .pathMatchers(HttpMethod.DELETE, "/**")
                    .authenticated()
                    .anyExchange()
                    .authenticated())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}
