package com.example.kotlin.fitness.demo.vic.service

import com.example.kotlin.fitness.demo.vic.builders.AuditBuilder.Companion.create
import com.example.kotlin.fitness.demo.vic.entity.Audit
import com.example.kotlin.fitness.demo.vic.entity.User
import com.example.kotlin.fitness.demo.vic.mappers.AuditMapper
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputAuditDTO
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import com.example.kotlin.fitness.demo.vic.storage.IAuditStorage
import com.example.kotlin.fitness.demo.vic.util.EntityType
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class AuditService : IAuditService {
    @Autowired
    private val storage: IAuditStorage? = null
    private val auditMapper: AuditMapper? = null

    @Transactional
    override fun create(
        user: User?,
        entityType: EntityType?,
        text: String?,
        idEntity: String?
    ): Audit? {
        return storage!!.save(
            create()
                .setId(UUID.randomUUID())
                .setDtCreate(Clock.systemUTC().millis())
                .setDtUpdate(Clock.systemUTC().millis())
                .setText(text)
                .setType(entityType)
                .setUid(idEntity)
                .setUser(user)
                .build()
        )
    }

    override fun get(pag: Pageable?): OutPage<OutputAuditDTO?>? {
        val auditPage = storage!!.findAll(
            pag!!
        )
        return auditMapper!!.map(auditPage)
    }

    override fun getById(uuid: String?): List<OutputAuditDTO?>? {
        val audits = storage!!.findByUid(uuid)
        return auditMapper!!.onceMap(audits)
    }

    override fun read(uuid: UUID?): Audit? {
        return null
    }
}