package com.example.pepega.common.controller

import com.example.pepega.common.dto.sign.request.SignInRequestDto
import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.dto.sign.response.SignInResponseDto
import com.example.pepega.common.model.response.CommonResult
import com.example.pepega.common.model.response.SingleResult
import com.example.pepega.common.service.response.ResponseService
import com.example.pepega.common.service.sign.SignService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign/v1")
class SignController(
    private val signService: SignService,
    private val responseService: ResponseService
) {

    @PostMapping("/signUp")
    fun signUp(@Validated @RequestBody signUpRequestDto: SignUpRequestDto
    ) : ResponseEntity<CommonResult> {
        signService.signUp(signUpRequestDto)
        return ResponseEntity.ok()
            .body(responseService.successResult())
    }

    @PostMapping("/signIn")
    fun signIn(
        @Validated @RequestBody signInRequestDto: SignInRequestDto
    ): ResponseEntity<SingleResult<SignInResponseDto>> {
        return ResponseEntity.ok()
            .body(responseService.singleResult(
                signService.signIn(signInRequestDto)
            ))
    }
}
