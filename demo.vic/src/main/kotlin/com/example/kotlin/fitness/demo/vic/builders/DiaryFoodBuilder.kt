package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.DiaryFood
import com.example.kotlin.fitness.demo.vic.entity.Dish
import com.example.kotlin.fitness.demo.vic.entity.Product
import java.util.*

class DiaryFoodBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var dtSupply: Long? = null
    private var dish: Dish? = null
    private var weightDish = 0.0
    private var product: Product? = null
    private var weightProduct = 0.0
    private var profile: UUID? = null
    fun setId(id: UUID?): DiaryFoodBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): DiaryFoodBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): DiaryFoodBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setDtSupply(dtSupply: Long?): DiaryFoodBuilder {
        this.dtSupply = dtSupply
        return this
    }

    fun setDish(dish: Dish?): DiaryFoodBuilder {
        this.dish = dish
        return this
    }

    fun setWeightDish(weightDish: Double): DiaryFoodBuilder {
        this.weightDish = weightDish
        return this
    }

    fun setProduct(product: Product?): DiaryFoodBuilder {
        this.product = product
        return this
    }

    fun setWeightProduct(weightProduct: Double): DiaryFoodBuilder {
        this.weightProduct = weightProduct
        return this
    }

    fun setProfile(profile: UUID?): DiaryFoodBuilder {
        this.profile = profile
        return this
    }

    fun build(): DiaryFood {
        return DiaryFood(id, dtCreate, dtUpdate, dtSupply, dish, weightDish, product, weightProduct, profile)
    }

    companion object {
        fun create(): DiaryFoodBuilder {
            return DiaryFoodBuilder()
        }
    }
}