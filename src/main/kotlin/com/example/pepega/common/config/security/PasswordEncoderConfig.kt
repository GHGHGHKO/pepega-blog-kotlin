package com.example.pepega.common.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordEncoderConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(
        VERSION, LOG_ROUND
    )

    companion object {
        private const val LOG_ROUND = 12
        private val VERSION = BCryptPasswordEncoder.BCryptVersion.`$2A`
    }
}
