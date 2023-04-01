package com.example.kotlin.fitness.demo.vic.idto

import com.example.kotlin.fitness.demo.vic.entity.Dish
import com.example.kotlin.fitness.demo.vic.entity.Product
import java.util.*
import javax.validation.constraints.Min

class InputDiaryFoodDTO(
    val dtSupply: Long,
    val dish: Dish,
    val weightDish: @Min(value = 0) Double,
    val product: Product,
    val weightProduct: @Min(value = 0) Double,
    val profile: UUID
)