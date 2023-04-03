package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputDiaryFoodDTO
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.service.api.IDiaryFoodService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
class DiaryFoodServlet {
    @Autowired
    private val service: IDiaryFoodService? = null
    private val CREATED = "The line in your dairy was successfully created"
    @PostMapping("/{uuid_profile}/journal/food")
    @Throws(LockException::class)
    protected fun post(
        @RequestBody idto: @Valid InputDiaryFoodDTO?,
        request: HttpServletRequest,
        @PathVariable(name = "uuid_profile") id: UUID?
    ): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val created = service!!.createWithParam(idto, authHeader, id)
        return ResponseEntity(CREATED, HttpStatus.CREATED)
    }

    @GetMapping("/{uuid_profile}/journal/food")
    protected fun getList(
        @PathVariable(name = "uuid_profile") id: UUID?,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): ResponseEntity<OutPage<*>?> {
        val pageable: Pageable = PageRequest.of(page, size)
        //OutPage<OutputDiaryFoodDTO> diary = service.get(pageable,id);
        val diary = service!![pageable, id]
        return ResponseEntity(diary, HttpStatus.OK)
    }
}