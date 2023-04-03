package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputProductDTO
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil
import com.example.kotlin.fitness.demo.vic.service.api.IProductService
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
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
class ProductServlet {
    @Autowired
    private val service: IProductService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null
    private val CREATED = "The Product was successfully added to library"
    private val UPDATED = "The Product was successfully updated "
    @PostMapping
    protected fun post(
        @RequestBody idto: @Valid InputProductDTO?,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val created = service!!.create(idto!!, authHeader)
        return ResponseEntity(CREATED, HttpStatus.CREATED)
    }

    @GetMapping
    protected fun getList(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): ResponseEntity<OutPage<*>?> {
        val pageable: Pageable = PageRequest.of(page, size)
        //OutPage<OutputProductDTO> products = this.service.get(pageable);
        val products = service!![pageable]
        return ResponseEntity(products, HttpStatus.OK)
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @Throws(LockException::class)
    protected fun doPut(
        @PathVariable(name = "uuid") id: UUID?,
        @PathVariable(name = "dt_update") dt_update: Long?,
        @RequestBody idto: @Valid InputProductDTO?,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        service!!.update(id, dt_update, idto!!, authHeader)
        return ResponseEntity.ok(UPDATED)
    }
}