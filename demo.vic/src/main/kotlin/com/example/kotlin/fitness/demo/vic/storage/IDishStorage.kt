package com.example.kotlin.fitness.demo.vic.storage

import com.example.kotlin.fitness.demo.vic.entity.Dish
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IDishStorage : JpaRepository<Dish?, UUID?> {
    override fun findAll(pageable: Pageable): Page<Dish?>
}