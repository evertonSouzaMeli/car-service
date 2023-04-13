package br.com.car

import br.com.car.adapters.bd.CarRepository
import br.com.car.adapters.http.CarHttpService
import br.com.car.core.service.CarServiceImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class CarServiceTest: FunSpec({
    val car = CarFixture.getCar()
    lateinit var carRepository: CarRepository
    lateinit var carHttpService: CarHttpService
    lateinit var carServiceImpl: CarServiceImpl

    beforeTest {
        carRepository = mockk {
           every { listAll() } returns listOf(car)
           every { listByModel(any()) } returns listOf(car)
        }

        carHttpService = mockk {
            /** Usamos coEvery quando é uma coroutine **/
            coEvery { getByModel(any()) } returns mockk()
        }

        carServiceImpl = CarServiceImpl(carRepository, carHttpService)
    }

    test("should throw a exception when not found car by id") {
        every { carRepository.findById(1) } returns null

        shouldThrow<RuntimeException> { carServiceImpl.findById(1) }
    }

    test("should return all items of specific model when carModel is not null") {
        carServiceImpl.list("Gol")

        verify (exactly = 1) { carRepository.listByModel(any()) }
        verify (exactly = 0) { carRepository.listAll() }
    }

    test("should return all items of cars when carModel is  null") {
        carServiceImpl.list(null)

        verify (exactly = 0) { carRepository.listByModel(any()) }
        verify (exactly = 1) { carRepository.listAll() }
    }
})