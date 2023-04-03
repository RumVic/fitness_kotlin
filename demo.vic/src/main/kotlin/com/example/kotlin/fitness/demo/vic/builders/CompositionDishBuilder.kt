package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import com.example.kotlin.fitness.demo.vic.entity.Product
import java.util.*

class CompositionDishBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var title: String? = null
    private var dish // reference to dish
            : UUID? = null
    private var product: Product? = null
    private var weight = 0.0
    fun setId(id: UUID?): CompositionDishBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): CompositionDishBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): CompositionDishBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setTitle(title: String?): CompositionDishBuilder {
        this.title = title
        return this
    }

    fun setDish(dish: UUID?): CompositionDishBuilder {
        this.dish = dish
        return this
    }

    fun setProduct(product: Product?): CompositionDishBuilder {
        this.product = product
        return this
    }

    fun setWeight(weight: Double): CompositionDishBuilder {
        this.weight = weight
        return this
    }

    fun build(): CompositionDish {
        return CompositionDish(id, dtCreate, dtUpdate, title, dish, product, weight)
    }

    companion object {
        fun create(): CompositionDishBuilder {
            return CompositionDishBuilder()
        }
    }
}