package com.example.pepega.common.dto.sign.request

import jakarta.validation.constraints.Email

data class SignInRequestDto(
    @Email
    val email: String,
    val password: String
)
