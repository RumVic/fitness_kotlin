package com.example.kotlin.fitness.demo.vic.storage

import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.util.*

interface IProfileStorage : JpaRepository<Profile?, UUID?> {
    override fun findById(@NonNull id: UUID): Optional<Profile?>
}