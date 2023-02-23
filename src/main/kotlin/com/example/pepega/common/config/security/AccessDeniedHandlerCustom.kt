package com.example.pepega.common.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class AccessDeniedHandlerCustom(
    @Qualifier("handlerExceptionResolver")
    private val handlerExceptionResolver: HandlerExceptionResolver
): AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        handlerExceptionResolver.resolveException(
            request, response, null, accessDeniedException)
    }
}
