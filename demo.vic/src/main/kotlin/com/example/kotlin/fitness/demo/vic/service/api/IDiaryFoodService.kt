package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.entity.DiaryFood
import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputDiaryFoodDTO
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputDiaryFoodDTO
import org.springframework.data.domain.Pageable
import java.util.*

interface IDiaryFoodService : IService<DiaryFood?, InputDiaryFoodDTO?, OutputDiaryFoodDTO?> {
    @Throws(LockException::class)
    fun createWithParam(idto: InputDiaryFoodDTO?, header: String?, uuid: UUID?): DiaryFood?
    fun getListOfLine(id: UUID?): List<DiaryFood?>?
    operator fun get(pageable: Pageable?, id: UUID?): OutPage<*>?
}