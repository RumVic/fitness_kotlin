package com.example.kotlin.fitness.demo.vic.storage

import com.example.kotlin.fitness.demo.vic.entity.Audit
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.util.*

interface IAuditStorage : JpaRepository<Audit?, UUID?> {
    override fun findById(@NonNull id: UUID): Optional<Audit?>

    // List<Audit> findAllById(Iterable<UUID> ids);
    override fun findAll(pageable: Pageable): Page<Audit?>
    fun findByUid(id: String?): List<Audit?>?
}