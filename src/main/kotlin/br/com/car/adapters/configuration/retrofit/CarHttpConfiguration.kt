package br.com.car.adapters.configuration.retrofit

import br.com.car.adapters.configuration.circuitbreaker.CircuitBreakerConfiguration
import br.com.car.adapters.http.CarHttpService
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Nessa classe fazemos as configurações de comunicação com outras APIs **/
@Configuration
class CarHttpConfiguration(
    private val circuitBreakerConfiguration: CircuitBreakerConfiguration
) {

    @Value(value = "\${host_url}")
    private lateinit var host: String

    /** Responsável por fazer a requisição http **/
    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreakerConfiguration.getCircuitBreaker()))
        .client(buildClient())
        .build()

    /** Recurso que vai ser gerenciado pelo Spring em tempo de execução [injeção de dependencia] **/
    @Bean
    fun carHttpService(): CarHttpService = buildRetrofit().create(CarHttpService::class.java)
}