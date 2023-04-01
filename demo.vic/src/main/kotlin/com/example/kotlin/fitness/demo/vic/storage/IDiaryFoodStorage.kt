package com.example.kotlin.fitness.demo.vic.storage

import com.example.kotlin.fitness.demo.vic.entity.DiaryFood
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.util.*

interface IDiaryFoodStorage : JpaRepository<DiaryFood?, UUID?> {
    override fun findById(@NonNull id: UUID): Optional<DiaryFood?>
    fun findAllByProfile(pageable: Pageable?, profileUuid: UUID?): Page<DiaryFood?>?
}