package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.idto.InputProfileDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputProfileDTO
import com.example.kotlin.fitness.demo.vic.service.api.IProfileService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
class ProfileServlet {
    private val CREATED = "New Profile was successfully created "
    private val service: IProfileService? = null
    @PostMapping
    protected fun post(@RequestBody idto: InputProfileDTO, request: HttpServletRequest): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val created = service!!.create(idto, authHeader)
        val uid = created.id.toString()
        return ResponseEntity(CREATED + "id : " + uid, HttpStatus.CREATED)
    }

    @GetMapping("/{uuid_profile}")
    protected fun getById(@PathVariable(name = "uuid_profile") id: UUID?): ResponseEntity<OutputProfileDTO> {
        return ResponseEntity.ok(service!!.readById(id))
    }
}