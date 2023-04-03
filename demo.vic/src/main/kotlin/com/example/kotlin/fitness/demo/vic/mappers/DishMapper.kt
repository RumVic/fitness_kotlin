package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import com.example.kotlin.fitness.demo.vic.entity.Dish
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputComDishDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputDishDTO
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class DishMapper {
    fun map(dishes: Page<Dish>): OutPage<OutputDishDTO> {
        val outPage = OutPage<OutputDishDTO>()
        outPage.number = dishes.number
        outPage.size = dishes.size
        outPage.totalPages = dishes.totalPages
        outPage.totalElements = dishes.totalElements.toInt()
        outPage.isFirst = dishes.isFirst
        outPage.numberOfElements = dishes.numberOfElements
        outPage.isLast = dishes.isLast
        val list: MutableList<OutputDishDTO> = ArrayList()
        for (dish in dishes.content) {
            val dto = builderDish(dish)
            list.add(dto)
        }
        outPage.content = list
        return outPage
    }

    fun builderDish(dish: Dish): OutputDishDTO {
        val dto = OutputDishDTO()
        dto.id = dish.id
        dto.dtCreate = dish.dtCreate
        dto.dtUpdate = dish.dtUpdate
        dto.title = dish.title
        dto.createByRole = dish.createByRole
        val comDish = dish.compositionDishList
        dto.compositionDishes = builderOutComDishList(comDish)
        return dto
    }

    fun builderOutComDishList(comDish: List<CompositionDish>?): List<OutputComDishDTO> {
        val listComDish: MutableList<OutputComDishDTO> = ArrayList()
        for (comDishD in comDish!!) {
            val outDto = builderOutComDish(comDishD)
            listComDish.add(outDto)
        }
        return listComDish
    }

    fun builderOutComDish(comDish: CompositionDish): OutputComDishDTO {
        val dto = OutputComDishDTO()
        dto.weight = comDish.weight
        val productMapper = ProductMapper()
        dto.product = productMapper.fromEntityToOutput(comDish.product!!)
        return dto
    }
}