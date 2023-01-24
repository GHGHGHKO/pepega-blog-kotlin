package com.example.pepega.common.service.sign

import com.example.pepega.common.advice.UserExistExceptionCustom
import com.example.pepega.common.domain.UserMaster
import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.repository.UserMasterRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(
    private val userMasterRepository: UserMasterRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(signUpRequestDto: SignUpRequestDto) {
        val user = userMasterRepository.findByEmail(signUpRequestDto.email)

        if (user != null) {
            throw UserExistExceptionCustom()
        }

        val roles = mutableListOf<String>()
        roles.add("ROLE_USER")

        userMasterRepository.save(
            UserMaster(
                email = signUpRequestDto.email,
                password = passwordEncoder.encode(signUpRequestDto.password),
                nickName = signUpRequestDto.nickname,
                createUser = SIGN_UP_SERVICE_NAME,
                updateUser = SIGN_UP_SERVICE_NAME,
                roles = roles
            )
        )
    }

    companion object{
        const val SIGN_UP_SERVICE_NAME = "SIGN_UP"
    }
}
