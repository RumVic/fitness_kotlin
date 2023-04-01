package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.Product
import java.util.*

class ProductBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var title: String? = null
    private var weight = 0.0
    private var calories = 0.0
    private var proteins = 0.0
    private var fats = 0.0
    private var carbohydrates = 0.0
    private var createdByRole: String? = null
    fun setId(id: UUID?): ProductBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): ProductBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): ProductBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setTitle(title: String?): ProductBuilder {
        this.title = title
        return this
    }

    fun setWeight(weight: Double): ProductBuilder {
        this.weight = weight
        return this
    }

    fun setCalories(calories: Double): ProductBuilder {
        this.calories = calories
        return this
    }

    fun setProteins(proteins: Double): ProductBuilder {
        this.proteins = proteins
        return this
    }

    fun setFats(fats: Double): ProductBuilder {
        this.fats = fats
        return this
    }

    fun setCarbohydrates(carbohydrates: Double): ProductBuilder {
        this.carbohydrates = carbohydrates
        return this
    }

    fun setCreatedByRole(createdByRole: String?): ProductBuilder {
        this.createdByRole = createdByRole
        return this
    }

    fun build(): Product {
        return Product(id, dtCreate, dtUpdate, title, weight, calories, proteins, fats, carbohydrates, createdByRole)
    }

    companion object {
        @JvmStatic
        fun create(): ProductBuilder {
            return ProductBuilder()
        }
    }
}