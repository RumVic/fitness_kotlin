package com.example.kotlin.fitness.demo.vic.mappers

import com.example.kotlin.fitness.demo.vic.entity.Audit
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputAuditDTO
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class AuditMapper {
    fun map(auditPages: Page<Audit>): OutPage<OutputAuditDTO> {
        val outPage = OutPage<OutputAuditDTO>()
        outPage.number = auditPages.number
        outPage.size = auditPages.size
        outPage.totalPages = auditPages.totalPages
        outPage.totalElements = auditPages.totalElements.toInt()
        outPage.isFirst = auditPages.isFirst
        outPage.numberOfElements = auditPages.numberOfElements
        outPage.isLast = auditPages.isLast
        val dtoList: MutableList<OutputAuditDTO> = ArrayList()
        for (audit in auditPages.content) {
            val auditDTO = builder(audit)
            dtoList.add(auditDTO)
        }
        outPage.content = dtoList
        return outPage
    }

    fun builder(audit: Audit): OutputAuditDTO {
        val auditDTO = OutputAuditDTO()
        val userMapper = UserMapper()
        auditDTO.user = userMapper.onceMap(audit.user)
        auditDTO.text = audit.text
        auditDTO.type = audit.type
        auditDTO.uid = audit.uid
        return auditDTO
    }

    fun onceMap(list: List<Audit>): List<OutputAuditDTO> {
        val dtoList: MutableList<OutputAuditDTO> = ArrayList()
        for (audit in list) {
            val outputAuditDTO = builder(audit)
            dtoList.add(outputAuditDTO)
        }
        return dtoList
    }
}