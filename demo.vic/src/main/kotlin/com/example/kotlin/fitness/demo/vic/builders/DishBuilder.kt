package com.example.kotlin.fitness.demo.vic.builders

import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import com.example.kotlin.fitness.demo.vic.entity.Dish
import java.util.*

class DishBuilder private constructor() {
    private var id: UUID? = null
    private var dtCreate: Long? = null
    private var dtUpdate: Long? = null
    private var title: String? = null
    private var comDishDTO: List<CompositionDish>? = null
    private var createByRole: String? = null
    fun setId(id: UUID?): DishBuilder {
        this.id = id
        return this
    }

    fun setDtCreate(dtCreate: Long?): DishBuilder {
        this.dtCreate = dtCreate
        return this
    }

    fun setDtUpdate(dtUpdate: Long?): DishBuilder {
        this.dtUpdate = dtUpdate
        return this
    }

    fun setTitle(title: String?): DishBuilder {
        this.title = title
        return this
    }

    fun setCompositionDish(comDishDTO: List<CompositionDish>?): DishBuilder {
        this.comDishDTO = comDishDTO
        return this
    }

    fun setCreateByRole(createByRole: String?): DishBuilder {
        this.createByRole = createByRole
        return this
    }

    fun build(): Dish {
        return Dish(id, dtCreate, dtUpdate, title, comDishDTO, createByRole)
    }

    companion object {
        fun create(): DishBuilder {
            return DishBuilder()
        }
    }
}