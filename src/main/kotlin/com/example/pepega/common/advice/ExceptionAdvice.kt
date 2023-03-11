package com.example.pepega.common.advice

import com.example.pepega.common.model.response.CommonResult
import com.example.pepega.common.service.response.ResponseService
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice(
    private val responseService: ResponseService,
    private val messageSource: MessageSource
) {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun defaultException(request: HttpServletRequest, e: Exception
    ): ResponseEntity<CommonResult> {

        log.error("Exception endPoint: ${request.method} ${request.requestURI}, " +
                "queryString : ${request.queryString}, " +
                "exception : $e")

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseService.failResult(
                getMessage("unKnown.code").toInt(),
                getMessage("unKnown.message")))
    }

    @ExceptionHandler(value = [UserExistExceptionCustom::class])
    @ResponseBody
    fun userExistException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(responseService.failResult(
                getMessage("existingUser.code").toInt(),
                getMessage("existingUser.message")))
    }

    @ExceptionHandler(value = [UserNotFoundExceptionCustom::class])
    fun userNotFoundException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(responseService.failResult(
                getMessage("userNotFound.code").toInt(),
                getMessage("userNotFound.message")))
    }

    @ExceptionHandler(value = [PasswordNotMatchExceptionCustom::class])
    fun passwordNotMatchException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(responseService.failResult(
                getMessage("passwordNotFound.code").toInt(),
                getMessage("passwordNotFound.message")))
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    fun authenticationEntryPointException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(responseService.failResult(
                getMessage("entryPointException.code").toInt(),
                getMessage("entryPointException.message")))
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun accessDeniedException(): ResponseEntity<CommonResult> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(responseService.failResult(
                getMessage("accessDenied.code").toInt(),
                getMessage("accessDenied.message")))
    }

    private fun getMessage(code: String): String {
        return messageSource.getMessage(code, null,
            LocaleContextHolder.getLocale())
    }
}
