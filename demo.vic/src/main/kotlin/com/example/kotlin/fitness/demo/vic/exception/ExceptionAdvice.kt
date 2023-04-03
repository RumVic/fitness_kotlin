package com.example.kotlin.fitness.demo.vic.exception

import com.example.kotlin.fitness.demo.vic.costom.JwtAuthenticationException
import com.example.kotlin.fitness.demo.vic.odto.OutputResponseError
import jakarta.persistence.OptimisticLockException
import jakarta.servlet.ServletException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.lang.Nullable
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import javax.naming.AuthenticationException

@RestControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(OptimisticLockException::class)
    fun processingException(e: OptimisticLockException): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = e.message
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(LockException::class)
    fun processingException(l: LockException): ResponseEntity<String> {
        return ResponseEntity(l.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun processingException(i: IllegalStateException): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = i.message
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun processingException(n: NoSuchElementException): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = n.message
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ServletException::class)
    fun jwtException(s: ServletException): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = s.message
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun security(s: AuthenticationException): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = s.message
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IOException::class)
    fun handleException(e: IOException?): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = "The server couldn't process request . Please contact to admin "
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(JwtAuthenticationException::class)
    fun jwtAuthExpn(e: JwtAuthenticationException?): ResponseEntity<SingleErrorResponse> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = "The server couldn't process request . Please contact to admin"
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    protected fun handleExceptionInternal(
        e: Exception?,
        @Nullable body: Any?,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val response = SingleErrorResponse()
        response.logref = "error"
        response.message = "The server couldn't process request . Please contact to admin"
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    protected fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException?,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    protected fun handleTypeMismatch(
        ex: TypeMismatchException?,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    protected fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException?,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(
        MissingRequestHeaderException::class
    )
    fun handleMissingRequestHeaderException(e: MissingRequestHeaderException): OutputResponseError {
        return OutputResponseError(HttpStatus.FORBIDDEN.toString(), e.message)
    }

    protected fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val response = MultipleErrorResponse()
        response.logref = "structured_error"
        val list: MutableList<SingleError> = ArrayList()
        for (fieldError in ex.bindingResult.fieldErrors) {
            val singleError = SingleError()
            singleError.field = fieldError.field
            singleError.message = fieldError.defaultMessage
            list.add(singleError)
        }
        response.errors = list
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}