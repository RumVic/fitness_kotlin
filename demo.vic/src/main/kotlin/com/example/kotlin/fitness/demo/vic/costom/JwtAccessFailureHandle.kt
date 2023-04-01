package com.example.kotlin.fitness.demo.vic.costom

import com.example.kotlin.fitness.demo.vic.odto.OutputResponseError
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.io.IOException
import java.io.OutputStream

class JwtAccessFailureHandle : AuthenticationFailureHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse, exception: AuthenticationException
    ) {
        val re = OutputResponseError(
            HttpStatus.BAD_REQUEST.toString(),
            "You need to pass url properly"
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_BAD_REQUEST
        val responseStream: OutputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, re)
        responseStream.flush()
    }
}