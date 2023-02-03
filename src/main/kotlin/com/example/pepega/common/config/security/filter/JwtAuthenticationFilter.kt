package com.example.pepega.common.config.security.filter

import com.example.pepega.common.config.security.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter (
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val jwtToken: String? = jwtTokenProvider
            .resolveToken(request as HttpServletRequest)

        if ((jwtToken != null) &&
            jwtTokenProvider.validateToken(jwtToken)) {

            val authentication = jwtTokenProvider
                .userAuthentication(jwtToken)
            SecurityContextHolder
                .getContext()
                .authentication = authentication
        }
        chain.doFilter(request, response)
    }
}
