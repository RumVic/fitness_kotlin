package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputUserByAdmin
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.odto.OutputUserDTO
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil
import com.example.kotlin.fitness.demo.vic.service.UserService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.SignatureException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
class AdminServlet {
    private val CREATED = " USER with admin role created new USER"
    private val UPDATED = " USER with admin role updated USER"

    @Autowired
    private val service: UserService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null
    @PostMapping("/users")
    fun registrationByAdmin(@RequestBody inputUserDTO: @Valid InputUserByAdmin?): ResponseEntity<String> {
        val created = service!!.createNewUser(inputUserDTO)
        return ResponseEntity(CREATED, HttpStatus.OK)
    }

    @GetMapping("/users")
    fun getUsers(
        request: HttpServletRequest?,
        @RequestParam size: Int,
        @RequestParam page: Int
    ): ResponseEntity<OutPage<*>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(service!![pageable])
    }

    @GetMapping("/users/{uuid}")
    fun getUserById(@PathVariable(name = "uuid") uuid: UUID?): ResponseEntity<OutputUserDTO> {
        return ResponseEntity.ok(service!!.readInput(uuid))
    }

    @PutMapping("/users/{uuid}/dt_update/{dt_update}")
    @Throws(LockException::class, SignatureException::class)
    fun updateUser(
        @RequestBody idto: @Valid InputUserByAdmin?,
        request: HttpServletRequest,
        @PathVariable(name = "uuid") id: UUID?,
        @PathVariable(name = "dt_update") dtUpdate: Long?
    ): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val updateUser = service!!.update(id, dtUpdate, idto, authHeader)
        return ResponseEntity(UPDATED, HttpStatus.OK)
    }
}