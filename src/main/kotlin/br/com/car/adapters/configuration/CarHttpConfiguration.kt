package br.com.car.adapters.configuration

import br.com.car.adapters.http.CarHttpService
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Nessa classe fazemos as configurações de comunicação com outras APIs **/
@Configuration
class CarHttpConfiguration {

    @Value(value = "\${host_url}")
    lateinit var host: String

    /** Responsável por fazer a requisição http **/
    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    /** Recurso que vai ser gerenciado pelo Spring em tempo de execução [injeção de dependencia] **/
    @Bean
    fun carHttpService(): CarHttpService {
        System.err.println(host)
        return buildRetrofit().create(CarHttpService::class.java)
    }
}