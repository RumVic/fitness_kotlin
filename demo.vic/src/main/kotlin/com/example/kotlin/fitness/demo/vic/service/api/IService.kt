package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import org.springframework.data.domain.Pageable
import java.util.*

interface IService<ENTITY, IDTO, ODTO> {
    fun create(dto: IDTO, header: String?): ENTITY
    fun read(id: UUID?): ENTITY
    operator fun get(pageable: Pageable?): OutPage<*>?

    @Throws(LockException::class)
    fun update(id: UUID?, dtUpdate: Long?, item: IDTO, header: String?): ENTITY
    fun delete(entity: ENTITY)
}