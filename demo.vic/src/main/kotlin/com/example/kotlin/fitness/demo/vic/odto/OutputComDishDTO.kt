package com.example.kotlin.fitness.demo.vic.odto

class OutputComDishDTO {
    var product: OutputProductDTO? = null
    var weight = 0.0

    constructor() {}
    constructor(product: OutputProductDTO?, weight: Double) {
        this.product = product
        this.weight = weight
    }
}