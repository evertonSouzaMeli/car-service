package br.com.car.core.service.converter

import br.com.car.adapters.http.CarHttp
import br.com.car.domain.model.Car

object CarHttpToModelConverter {

    fun toModel(carsHttp: List<CarHttp>) =
        carsHttp.map {
            Car(
                id = Long.MAX_VALUE,
                name = it.model,
                model = it.make,
                year = it.year
            ).isEligibleToUber()
        }
}