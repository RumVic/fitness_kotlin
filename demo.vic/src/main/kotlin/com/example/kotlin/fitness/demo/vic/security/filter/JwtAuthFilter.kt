package com.example.kotlin.fitness.demo.vic.security.filter

import com.example.kotlin.fitness.demo.vic.costom.JwtAuthenticationException
import com.example.kotlin.fitness.demo.vic.storage.IUserStorage
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.SignatureException

@Slf4j
@Component
@RequiredArgsConstructor
class JwtAuthFilter : OncePerRequestFilter() {
    @Autowired
    private val userStorage: IUserStorage? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val email: String
        val jwtToken: String
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            //JwtAuthFilter.log.info("doFilterChain")
            return
        }
        try {
            jwtToken = authHeader.substring(7)
            email = jwtUtil!!.extractUsername(jwtToken)
            if (email != null && SecurityContextHolder.getContext().authentication == null) {
                val myUser: UserDetails? = userStorage!!.findByLogin(email)
                if (jwtUtil.validateToken(jwtToken, myUser!!)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        myUser,
                        null,
                        myUser.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        } catch (e: JwtAuthenticationException) {
            SecurityContextHolder.clearContext()
            response.sendError(e.hashCode())
            throw JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED)
        } catch (e: SignatureException) {
            SecurityContextHolder.clearContext()
            response.sendError(e.hashCode())
            throw JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED)
        } catch (e: MalformedJwtException) {
            SecurityContextHolder.clearContext()
            response.sendError(401, e.toString())
            throw MalformedJwtException("Error ")
        }
        /*    catch (SignatureException e){
            SecurityContextHolder.clearContext();
            response.sendError(401,e.toString());
            throw new SignatureException("fuck you ");
        }*/filterChain.doFilter(request, response)
    }
}