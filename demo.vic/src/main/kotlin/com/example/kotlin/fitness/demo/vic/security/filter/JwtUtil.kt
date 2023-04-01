package com.example.kotlin.fitness.demo.vic.security.filter

import io.jsonwebtoken.*
import io.jsonwebtoken.Jwts.claims
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import java.util.function.Function
import java.util.logging.Logger

private fun Logger.error(s: String, message: String?) {

}

@Slf4j
@Component
class JwtUtil {
    private val SECRET_KEY = "secret"

    @Throws(SignatureException::class)
    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    @Throws(SignatureException::class)
    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    @Throws(SignatureException::class)
    fun hasClaims(token: String?, claimName: String?): Boolean {
        val claims = extractAllClaims(token)
        return claims(claims) != null
    }

    @Throws(SignatureException::class)
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    @Throws(
        SignatureException::class,
        MalformedJwtException::class,
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        IllegalArgumentException::class
    )
    private fun extractAllClaims(token: String?): Claims {

        /* return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();*/
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
        } catch (ex :SignatureException) {
            //log.error("Invalid JWT signature - {}");
        } catch (ex: MalformedJwtException) {
            //log.error("Invalid JWT token - {}", ex.message)
            Logger.GLOBAL_LOGGER_NAME
        } catch (ex: ExpiredJwtException) {
            //log.error("Expired JWT token - {}", ex.message)
        } catch (ex: UnsupportedJwtException) {
            //log.error("Unsupported JWT token - {}", ex.message)
        } catch (ex: IllegalArgumentException) {
            //log.error("JWT claims string is empty - {}", ex.message)
        }
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    @Throws(SignatureException::class)
    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails, login: String): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, userDetails, login)
    }

    private fun createToken(claims: Map<String, Any>, userDetails: UserDetails, login: String): String {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(login) //.claim("login",login)
            .claim("authorities", userDetails.authorities)
            .claim("enabled", userDetails.isEnabled) //  add new
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()
    }

    @Throws(SignatureException::class)
    fun validateToken(
        token: String?,
        userDetails: UserDetails
    ): Boolean { //compare/valodate JWT data with the current user
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

}