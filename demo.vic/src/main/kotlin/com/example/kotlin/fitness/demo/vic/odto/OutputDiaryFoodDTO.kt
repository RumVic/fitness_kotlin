package com.example.kotlin.fitness.demo.vic.odto

import java.util.*

class OutputDiaryFoodDTO {
    var id: UUID? = null
    var dtCreate: Long? = null
    var dtUpdate: Long? = null
    var dtSupply: Long? = null
    var dish: OutputDishDTO? = null
    var weightDish = 0.0
    var product: OutputProductDTO? = null
    var weightProduct = 0.0
    var profile: UUID? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        dtSupply: Long?,
        dish: OutputDishDTO?,
        weightDish: Double,
        product: OutputProductDTO?,
        weightProduct: Double,
        profile: UUID?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.dtSupply = dtSupply
        this.dish = dish
        this.weightDish = weightDish
        this.product = product
        this.weightProduct = weightProduct
        this.profile = profile
    }
}