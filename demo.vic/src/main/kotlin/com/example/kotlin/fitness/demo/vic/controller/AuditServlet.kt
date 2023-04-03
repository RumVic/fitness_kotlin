package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputAuditDTO
import com.example.kotlin.fitness.demo.vic.service.api.IAuditService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
class AuditServlet {
    val service: IAuditService? = null
    @GetMapping("/audit")
    fun getPage(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): ResponseEntity<OutPage<*>?> {
        val pageable: Pageable = PageRequest.of(page, size)
        val outPage = service!![pageable]
        return ResponseEntity(outPage, HttpStatus.OK)
    }

    @GetMapping("/audit/{uuid}")
    fun getUserById(@PathVariable(name = "uuid") uuid: String?): ResponseEntity<out List<OutputAuditDTO>> {
        return ResponseEntity<List<OutputAuditDTO>>(service!!.getById(uuid), HttpStatus.OK)
    }
}