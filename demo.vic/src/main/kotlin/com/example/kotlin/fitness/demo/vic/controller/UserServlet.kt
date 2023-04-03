package com.example.kotlin.fitness.demo.vic.controller

import com.example.kotlin.fitness.demo.vic.idto.InputUserDTO
import com.example.kotlin.fitness.demo.vic.odto.OutputUserDTO
import com.example.kotlin.fitness.demo.vic.security.filter.JwtUtil
import com.example.kotlin.fitness.demo.vic.service.MailService
import com.example.kotlin.fitness.demo.vic.service.UserService
import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.SignatureException
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserServlet {
    private val CREATED = "The new User was created and waiting activation , please check your Email"

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val service: UserService? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Autowired
    private val mailService: MailService? = null
    @PostMapping("/registration")
    fun mailRegistration(@RequestBody inputUserDTO: @Valid InputUserDTO?): ResponseEntity<String> {
        mailService!!.addUser(inputUserDTO!!)
        return ResponseEntity(CREATED, HttpStatus.OK)
    }

    @PostMapping("/login")
    fun loginIn(@RequestBody request: InputUserDTO): ResponseEntity<String> {
        authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(request.mail, request.password)
        )
        val userDetails = service!!.loadUserByLogin(request.mail)
        return if (userDetails != null) {
            ResponseEntity.ok(jwtUtil!!.generateToken(userDetails, request.mail!!))
        } else ResponseEntity.status(400).body("Some error has occurred")
    }

    @GetMapping("/me")
    @Throws(SignatureException::class)
    fun getMe(request: HttpServletRequest): ResponseEntity<OutputUserDTO> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return ResponseEntity.ok(service!!.getMyInfo(authHeader))
    }
}