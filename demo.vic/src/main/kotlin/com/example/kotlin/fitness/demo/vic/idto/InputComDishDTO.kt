package com.example.kotlin.fitness.demo.vic.idto

import com.example.kotlin.fitness.demo.vic.entity.Product
import java.util.*

class InputComDishDTO {
    var id: UUID? = null
        private set
    var dtCreate: Long? = null
        private set
    var dtUpdate: Long? = null
        private set
    var title: String? = null
        private set
    var dish // reference to dish
            : UUID? = null
        private set
    var product: Product? = null
        private set
    var weight = 0.0
        private set

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        dish: UUID?,
        product: Product?,
        weight: Double
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.dish = dish
        this.product = product
        this.weight = weight
    }
}