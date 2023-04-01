package com.example.kotlin.fitness.demo.vic.costom

import lombok.Getter
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException

@Getter
class JwtAuthenticationException : AuthenticationException {
    private var httpStatus: HttpStatus? = null
    override val message: String? = null

    constructor(msg: String?, httpStatus: HttpStatus?) : super(msg) {
        //this.message = msg;
        this.httpStatus = httpStatus
    }

    constructor(msg: String?) : super(msg) {}
}