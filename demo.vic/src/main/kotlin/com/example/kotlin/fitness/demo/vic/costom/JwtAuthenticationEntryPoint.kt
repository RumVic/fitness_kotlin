package com.example.kotlin.fitness.demo.vic.costom

import com.example.kotlin.fitness.demo.vic.odto.OutputResponseError
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream

@Component("JwtAuthenticationEntryPoint")
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        /*resolver.resolveException(request, response, null, authenticationException);*/
        val re = OutputResponseError(
            HttpStatus.UNAUTHORIZED.toString(),
            "For commission request required to pass Authentication token "
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseStream: OutputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, re)
        responseStream.flush()
    }
}