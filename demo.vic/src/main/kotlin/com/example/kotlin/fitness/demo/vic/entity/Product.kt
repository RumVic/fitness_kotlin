package com.example.kotlin.fitness.demo.vic.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "product_fitness")
class Product {
    @Id
    var id: UUID? = null

    @Column(name = "dt_create")
    var dtCreate: Long? = null

    @Column(name = "dt_update")
    var dtUpdate: Long? = null
    var title: String? = null
    var weight = 0.0
    var calories = 0.0
    var proteins = 0.0
    var fats = 0.0
    var carbohydrates = 0.0

    @Column(name = "created_by_role")
    var createdByRole // who created product in db;
            : String? = null

    constructor() {}
    constructor(
        id: UUID?,
        dtCreate: Long?,
        dtUpdate: Long?,
        title: String?,
        weight: Double,
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double,
        createdByRole: String?
    ) {
        this.id = id
        this.dtCreate = dtCreate
        this.dtUpdate = dtUpdate
        this.title = title
        this.weight = weight
        this.calories = calories
        this.proteins = proteins
        this.fats = fats
        this.carbohydrates = carbohydrates
        this.createdByRole = createdByRole
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Product) return false
        val product = o
        return java.lang.Double.compare(
            product.weight,
            weight
        ) == 0 && java.lang.Double.compare(
            product.calories,
            calories
        ) == 0 && java.lang.Double.compare(
            product.proteins,
            proteins
        ) == 0 && java.lang.Double.compare(
            product.fats,
            fats
        ) == 0 && java.lang.Double.compare(
            product.carbohydrates,
            carbohydrates
        ) == 0 && id == product.id && dtCreate == product.dtCreate && dtUpdate == product.dtUpdate && title == product.title && createdByRole == product.createdByRole
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            dtCreate,
            dtUpdate,
            title,
            weight,
            calories,
            proteins,
            fats,
            carbohydrates,
            createdByRole
        )
    }

    override fun toString(): String {
        return "Product{" +
                "id=" + id +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", title='" + title + '\'' +
                ", weight=" + weight +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbohydrates=" + carbohydrates +
                ", createdByRole='" + createdByRole + '\'' +
                '}'
    }
}