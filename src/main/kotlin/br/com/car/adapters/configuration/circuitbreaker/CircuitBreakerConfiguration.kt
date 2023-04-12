package br.com.car.adapters.configuration.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class CircuitBreakerConfiguration {

    fun getConfiguration(): CircuitBreakerConfig = CircuitBreakerConfig
        /** Podemos usar a configuração padrão **/
        //.ofDefaults()
        .custom()
        /** Numeros de requisições para dar erro **/
        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
        /** A cada (n) requisições quantas % deram erro para abrir o circuit breaker **/
        .slidingWindowSize(10)
        /** A cada (n) requisições quantas % deram foram lentas para abrir o circuit breaker **/
        .slowCallRateThreshold(70.0F)
        /** A cada (n) requisições quantas % deram falha para abrir o circuit breaker **/
        .failureRateThreshold(70.0F)
        /** quanto o tempo o HALF_OPEN vai ficar aberto para conferir novamente o estado do microservice **/
        .waitDurationInOpenState(Duration.ofSeconds(5))
        /** Definição do que é uma requisição lenta **/
        //.slowCallDurationThreshold(Duration.ofSeconds(3))
        .slowCallDurationThreshold(Duration.ofNanos(3))
        /** Numero de requisições em HALF_OPEN antes de definir um estado **/
        .permittedNumberOfCallsInHalfOpenState(3)
        .build()

    /** Injeção da configuração **/
    fun getCircuitBreaker () = CircuitBreakerRegistry.of(getConfiguration())
        .circuitBreaker("circuit-breaker-car-service")
}