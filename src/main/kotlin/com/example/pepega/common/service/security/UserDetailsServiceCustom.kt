package com.example.pepega.common.service.security

import com.example.pepega.common.advice.UserNotFoundExceptionCustom
import com.example.pepega.common.repository.UserMasterRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserDetailsServiceCustom(
    private val userMasterRepository: UserMasterRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return userMasterRepository.findById(
            UUID.fromString(username)
        ).orElseThrow { UserNotFoundExceptionCustom() }
    }
}
