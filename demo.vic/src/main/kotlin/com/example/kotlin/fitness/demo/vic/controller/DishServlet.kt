package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.exception.LockException
import com.example.kotlin.fitness.demo.vic.idto.InputDishDTO
import com.example.kotlin.fitness.demo.vic.odto.OutPage
import com.example.kotlin.fitness.demo.vic.service.api.IDishService
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
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
class DishServlet {
    @Autowired
    private val service: IDishService? = null
    private val CREATED = "The recipe was successfully added to  the library"
    private val UPDATED = "The recipe was successfully updated"
    @PostMapping
    protected fun post(@RequestBody idto: @Valid InputDishDTO?, request: HttpServletRequest): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val created = service!!.create(idto, authHeader)
        return ResponseEntity(CREATED, HttpStatus.CREATED)
    }

    @GetMapping
    protected fun getList(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): ResponseEntity<OutPage<*>?> {
        val pageable: Pageable = PageRequest.of(page, size)
        //OutPage<OutputDishDTO> dish = service.get(pageable);
        val dish = service!![pageable]
        return ResponseEntity(dish, HttpStatus.OK)
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @Throws(LockException::class)
    protected fun doPut(
        @PathVariable(name = "uuid") id: UUID?,
        @PathVariable(name = "dt_update") dt_update: Long?,
        @RequestBody idto: @Valid InputDishDTO?,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        service!!.update(id, dt_update, idto, authHeader)
        return ResponseEntity(UPDATED, HttpStatus.OK)
    } //TODO NOT DEMANDED
    /*  @GetMapping("/id")//http://localhost:8080/recipe/id + id param
    protected ResponseEntity<Dish> getById(@RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(service.read(id));
    }*/
}