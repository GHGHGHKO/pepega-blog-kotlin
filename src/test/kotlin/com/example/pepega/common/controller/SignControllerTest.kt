package com.example.pepega.common.controller

import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.repository.UserMasterRepository
import com.fasterxml.jackson.databind.ObjectMapper

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class SignControllerTest (
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val userMasterRepository: UserMasterRepository,

    @Autowired
    val objectMapper: ObjectMapper,

    @Autowired
    val passwordEncoder: PasswordEncoder
) {

    @BeforeEach
    fun setUp() {


    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `회원가입 성공한다`() {

        val signUpRequestDto = SignUpRequestDto(
            email = "pepega@tistory.com",
            password = "q035o9gj3590y!@#",
            nickname = "rickRoll")

        mockMvc.post("/sign/v1/signUp") {
            content = objectMapper.writeValueAsString(signUpRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.success") { value(true) } }
            .andExpect { jsonPath("$.code") { value(0) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }

    @Test
    fun signIn() {
    }
}
