package com.example.pepega.common.dto.sign.response

import java.util.Date

data class SignInResponseDto(
    val token: String,
    val utcExpirationDate: Date,
    val roles: MutableList<String>
)
