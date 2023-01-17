package com.example.pepega.common.dto.sign

import jakarta.validation.constraints.Email

data class SignUpRequestDto(
    @field:Email
    var email: String,
    var password: String,
    var nickname: String
)
