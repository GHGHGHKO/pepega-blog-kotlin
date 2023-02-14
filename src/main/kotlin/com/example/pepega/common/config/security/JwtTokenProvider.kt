package com.example.pepega.common.config.security

import com.example.pepega.common.dto.jwt.TokenResponseDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider (
    private val userDetailsService: UserDetailsService
) {

    val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun createToken(
        primaryKey: String, roles: MutableList<String>
    ): TokenResponseDto {
        val claims = Jwts.claims().setSubject(primaryKey)
        val now = Date()
        val utcExpirationDate = Date(now.time + TOKEN_VALID_MILLISECOND)

        return TokenResponseDto(
            token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(utcExpirationDate)
                .signWith(secretKey)
                .compact(),
            utcExpirationDate = utcExpirationDate,
            roles = roles
        )
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(TOKEN_HEADER_NAME)
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)

            claimsJws
                .body
                .expiration
                .after(Date())

        } catch (e: Exception) {
            println(e)
            false
        }
    }

    fun userPrimaryKey(jwtToken: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey).build()
            .parseClaimsJws(jwtToken)
            .body
            .subject
    }

    @Transactional
    fun userAuthentication(jwtToken: String): Authentication {
        val userDetails = userDetailsService
            .loadUserByUsername(userPrimaryKey(jwtToken))

        return UsernamePasswordAuthenticationToken(
            userDetails, userDetails.password, userDetails.authorities
        )
    }

    companion object {
        private const val TOKEN_VALID_MILLISECOND = 1000L * 60 * 60
        private const val TOKEN_HEADER_NAME = "X-AUTH-TOKEN"
    }
}
