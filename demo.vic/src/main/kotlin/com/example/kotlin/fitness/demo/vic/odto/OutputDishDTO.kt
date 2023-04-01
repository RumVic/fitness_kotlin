package com.example.kotlin.fitness.demo.vic.odto

import java.util.*

class OutputDishDTO {
    var id: UUID? = null
    var dtCreate: Long? = null
    var dtUpdate: Long? = null
    var title: String? = null
    var compositionDishes: List<OutputComDishDTO>? = null
    var createByRole: String? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        compositionDishes: List<OutputComDishDTO>?,
        createByRole: String?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.compositionDishes = compositionDishes
        this.createByRole = createByRole
    }
}