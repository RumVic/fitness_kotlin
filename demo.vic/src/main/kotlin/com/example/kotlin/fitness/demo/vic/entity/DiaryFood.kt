package com.example.kotlin.fitness.demo.vic.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "diary_food_fitness")
class DiaryFood {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null

    @Column(name = "dt_supply")
    var dtSupply: Long? = null

    @ManyToOne(targetEntity = Dish::class)
    @JoinColumn
    var dish: Dish? = null

    @Column(name = "weight_dish")
    var weightDish = 0.0

    @ManyToOne(targetEntity = Product::class)
    var product: Product? = null

    @Column(name = "weight_product")
    var weightProduct = 0.0
    var profile: UUID? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        dtSupply: Long?,
        dish: Dish?,
        weightDish: Double,
        product: Product?,
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

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is DiaryFood) return false
        val diaryFood = o
        return java.lang.Double.compare(
            diaryFood.weightDish,
            weightDish
        ) == 0 && java.lang.Double.compare(
            diaryFood.weightProduct,
            weightProduct
        ) == 0 && id == diaryFood.id && dtCreate == diaryFood.dtCreate && dtUpdate == diaryFood.dtUpdate && dtSupply == diaryFood.dtSupply && dish == diaryFood.dish && product!!.equals(
            diaryFood.product
        ) && profile == diaryFood.profile
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            dtCreate,
            dtUpdate,
            dtSupply,
            dish,
            weightDish,
            product,
            weightProduct,
            profile
        )
    }
}