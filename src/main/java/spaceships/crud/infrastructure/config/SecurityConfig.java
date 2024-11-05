// package spaceships.crud.infrastructure.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.server.SecurityWebFilterChain;
//
// @Configuration
// @EnableWebFluxSecurity
// public class SecurityConfig {
//
//  @Bean
//  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
//    http.cors(Customizer.withDefaults())
//        .csrf(ServerHttpSecurity.CsrfSpec::disable)
//        .authorizeExchange(
//            exchanges ->
//
// exchanges.pathMatchers("/spaceships/**").authenticated().anyExchange().permitAll())
//        .httpBasic(Customizer.withDefaults());
//    return http.build();
//  }
//
//  @Bean
//  public MapReactiveUserDetailsService userDetailsService() {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    UserDetails user = User.withUsername("admin").password("admin").roles("ADMIN").build();
//    return new MapReactiveUserDetailsService(user);
//  }
// }
