package br.com.car

import br.com.car.domain.model.Car

object CarFixture {

    fun getCar() = Car(1, "gol", "VW", year = 2015)
}