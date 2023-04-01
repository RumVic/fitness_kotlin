package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import com.example.kotlin.fitness.demo.vic.idto.InputComDishDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputComDishDTO
import java.util.*

interface ICompositionDishService : IService<CompositionDish?, InputComDishDTO?, OutputComDishDTO?> {
    fun create(dto: List<InputComDishDTO?>?, idDish: UUID?): List<CompositionDish?>?
    fun update(dto: List<InputComDishDTO?>?, idDish: UUID?): List<CompositionDish?>?
    fun delete(compositionDish: List<CompositionDish?>?)
}