package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.Product
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputProductDTO
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class ProductMapper {
    fun map(productOutPage: Page<Product>): OutPage<OutputProductDTO> {
        val outPage = OutPage<OutputProductDTO>()
        outPage.number = productOutPage.number
        outPage.size = productOutPage.size
        outPage.totalPages = productOutPage.totalPages
        outPage.totalElements = productOutPage.totalElements.toInt()
        outPage.isFirst = productOutPage.isFirst
        outPage.numberOfElements = productOutPage.numberOfElements
        outPage.isLast = productOutPage.isLast
        val list: MutableList<OutputProductDTO> = ArrayList()
        for (product in productOutPage.content) {
            val productDTO = fromEntityToOutput(product)
            list.add(productDTO)
        }
        outPage.content = list
        return outPage
    }

    fun fromEntityToOutput(product: Product): OutputProductDTO {
        val dto = OutputProductDTO()
        dto.id = product.id
        dto.dtCreate = product.dtCreate
        dto.dtUpdate = product.dtUpdate
        dto.title = product.title
        dto.calories = product.calories
        dto.fats = product.fats
        dto.carbohydrates = product.carbohydrates
        dto.weight = product.weight
        dto.proteins = product.proteins
        return dto
    }
}