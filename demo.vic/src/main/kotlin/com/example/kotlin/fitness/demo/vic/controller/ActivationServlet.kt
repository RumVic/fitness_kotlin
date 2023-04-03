package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.service.MailService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@RestController
@RequestMapping("/activation")
@RequiredArgsConstructor
class ActivationServlet {
    private val CREATED = "Congratulations on your successful registration"
    private val mailService: MailService? = null
    @GetMapping("/{activation_code}/mail/{mail}")
    @Throws(IOException::class)
    fun getUserById(
        @PathVariable(name = "activation_code") code: String?,
        @PathVariable(name = "mail") mail: String?
    ): ResponseEntity<String> {
        mailService!!.activateUser(code)
        return ResponseEntity(CREATED, HttpStatus.OK)
    }
}