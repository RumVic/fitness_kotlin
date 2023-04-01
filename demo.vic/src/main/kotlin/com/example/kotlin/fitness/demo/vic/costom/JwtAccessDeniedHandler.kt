package com.example.kotlin.fitness.demo.vic.costom

import com.example.kotlin.fitness.demo.vic.odto.OutputResponseError
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        val re = OutputResponseError(
            HttpStatus.FORBIDDEN.toString(),
            "Your role is not according requested operation"
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_FORBIDDEN
        val responseStream: OutputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, re)
        responseStream.flush()
    }
}