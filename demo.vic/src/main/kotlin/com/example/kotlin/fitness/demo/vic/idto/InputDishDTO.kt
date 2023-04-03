package com.example.kotlin.fitness.demo.vic.idto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class InputDishDTO {
    //javax validation constraint
    var title: @NotNull String? = null
        private set
    private var comDishDTO: @NotEmpty MutableList<InputComDishDTO>? = null

    constructor() {}
    constructor(title: String?, comDishDTO: List<InputComDishDTO>?) {
        this.title = title
        this.comDishDTO = comDishDTO
    }

    fun getComDishDTO(): List<InputComDishDTO>? {
        return comDishDTO
    }
}