package com.example.kotlin.fitness.demo.vic.odto

import java.util.*

class OutputProductDTO {
    var id: UUID? = null
    var dtCreate: Long? = null
    var dtUpdate: Long? = null
    var title: String? = null
    var weight = 0.0
    var calories = 0.0
    var proteins = 0.0
    var fats = 0.0
    var carbohydrates = 0.0

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        weight: Double,
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.weight = weight
        this.calories = calories
        this.proteins = proteins
        this.fats = fats
        this.carbohydrates = carbohydrates
    }
}