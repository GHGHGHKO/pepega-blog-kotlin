package com.example.pepega.common.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class AuthenticationEntryPointCustom(
    @Qualifier("handlerExceptionResolver")
    private val handlerExceptionResolver: HandlerExceptionResolver
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        handlerExceptionResolver.resolveException(
            request, response, null, authException)
    }
}
