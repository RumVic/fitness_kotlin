package com.example.kotlin.fitness.demo.vic.idto

import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class InputProductDTO(
    val title: @NotNull String?,
    val weight: @Min(value = 0, message = "No can have this value") Double,
    val calories: @Min(value = 0, message = "No can have this value") Double,
    val proteins: @Min(value = 0, message = "No can have this value") Double,
    val fats: @Min(value = 0, message = "No can have this value") Double,
    val carbohydrates: @Min(value = 0, message = "No can have this value") Double
) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is InputProductDTO) return false
        val inputDTO = o
        return java.lang.Double.compare(inputDTO.weight, weight) == 0 && java.lang.Double.compare(
            inputDTO.calories,
            calories
        ) == 0 && java.lang.Double.compare(inputDTO.proteins, proteins) == 0 && java.lang.Double.compare(
            inputDTO.fats,
            fats
        ) == 0 && java.lang.Double.compare(inputDTO.carbohydrates, carbohydrates) == 0 && title == inputDTO.title
    }

    override fun hashCode(): Int {
        return Objects.hash(title, weight, calories, proteins, fats, carbohydrates)
    }

    override fun toString(): String {
        return "InputDTO{" +
                "title='" + title + '\'' +
                ", weight=" + weight +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                '}'
    }
}