package com.example.kotlin.fitness.demo.vic.storage

import com.example.kotlin.fitness.demo.vic.entity.CompositionDish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.util.*

interface ICompositionDishStorage : JpaRepository<CompositionDish?, UUID?> {
    override fun findById(@NonNull id: UUID): Optional<CompositionDish?>
}