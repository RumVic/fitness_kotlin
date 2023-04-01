package com.example.kotlin.fitness.demo.vic.costom

import org.springframework.http.HttpStatus
import java.security.SignatureException

class SignatureException : SignatureException {
    private var httpStatus: HttpStatus? = null
    constructor(msg: String?, httpStatus: HttpStatus?) : super(msg) {
        this.httpStatus = httpStatus
    }
    constructor(msg: String?) : super(msg) {}
}