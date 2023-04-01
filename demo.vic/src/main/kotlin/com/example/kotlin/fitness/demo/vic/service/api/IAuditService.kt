package com.example.kotlin.fitness.demo.vic.service.api

import com.example.kotlin.fitness.demo.vic.entity.Audit
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputAuditDTO
import com.example.kotlin.fitness.demo.vic.util.EntityType
import org.springframework.data.domain.Pageable
import java.util.*

interface IAuditService {
    fun create(user: User?, entityType: EntityType?, text: String?, idEntity: String?): Audit?
    operator fun get(pag: Pageable?): OutPage<OutputAuditDTO?>?
    fun read(uuid: UUID?): Audit?
    fun getById(uuid: String?): List<OutputAuditDTO?>?
}