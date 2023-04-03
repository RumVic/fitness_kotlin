package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.DiaryFood
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputDiaryFoodDTO
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class DiaryFoodMapper {
    fun map(page: Page<DiaryFood>): OutPage<OutputDiaryFoodDTO> {
        val outDto = OutPage<OutputDiaryFoodDTO>()
        outDto.number = page.number
        outDto.size = page.size
        outDto.totalPages = page.totalPages
        outDto.totalElements = page.totalElements.toInt()
        outDto.isFirst = page.isFirst
        outDto.numberOfElements = page.numberOfElements
        outDto.isLast = page.isLast
        val listDiary: MutableList<OutputDiaryFoodDTO> = ArrayList()
        for (diary in page.content) {
            val dto = OutputDiaryFoodDTO()
            dto.id = diary.id
            dto.dtCreate = diary.dtCreate
            dto.dtUpdate = diary.dtUpdate
            dto.dtSupply = diary.dtSupply
            dto.profile = diary.profile
            if (diary.dish != null) {
                val dishMapper = DishMapper()
                dto.dish = dishMapper.builderDish(diary.dish)
                dto.weightDish = diary.weightDish
            } else dto.dish = null
            if (diary.product != null) {
                val productMapper = ProductMapper()
                dto.product = productMapper.fromEntityToOutput(diary.product!!)
                dto.weightProduct = diary.weightProduct
            } else dto.product = null
            listDiary.add(dto)
        }
        outDto.content = listDiary
        return outDto
    }
}