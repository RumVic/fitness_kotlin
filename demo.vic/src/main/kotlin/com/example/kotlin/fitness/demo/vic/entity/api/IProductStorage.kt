package com.example.kotlin.fitness.demo.vic.entity.api

import com.example.kotlin.fitness.demo.vic.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.util.*

interface IProductStorage : JpaRepository<Product?, UUID?> {

    override fun findById(@NonNull id: UUID): Optional<Product?>
    override fun findAll(pageable: Pageable): Page<Product?>
}