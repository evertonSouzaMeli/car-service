package br.com.car.adapters.api

import br.com.car.domain.model.Car
import br.com.car.domain.ports.CarService
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarResource(
    private val carService: CarService
) {
    @GetMapping
    fun list(@RequestParam(required = false) model: String?) = carService.list(model)

    @PostMapping
    fun save(@RequestBody car: Car) = carService.save(car)

    @PutMapping("/{id}")
    fun update(@RequestBody car: Car, @PathVariable id: Long) = carService.update(car, id)

    @GetMapping("/lista-modelo")
    fun listByInventory(@RequestParam model: String) =
    /** Com runBlocking abrimos uma requisição suspensa [suspend fun],
     * Quer dizer que começa a virtual thread requisição, mas não necessariamente termina
     * Outra Virtual Thread pode terminar a requisição **/
        runBlocking {
            carService.listByInventory(model)
        }
}

