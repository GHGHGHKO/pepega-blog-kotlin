package com.example.pepega.common.controller

import com.example.pepega.common.dto.sign.request.SignInRequestDto
import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.service.sign.SignService
import com.fasterxml.jackson.databind.ObjectMapper

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
    val signService: SignService,

    @Autowired
    val objectMapper: ObjectMapper
) {

    companion object {
        private const val EXIST_EMAIL = "existUser@real.com"
        private const val EXIST_PASSWORD = "existPassword!@#s2"
        private const val EXIST_NICKNAME = "patric"
    }

    @BeforeEach
    fun setUp() {
        val signUpRequestDto = SignUpRequestDto(
            email = EXIST_EMAIL,
            password = EXIST_PASSWORD,
            nickname = EXIST_NICKNAME
        )

        signService.signUp(signUpRequestDto)
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
    fun `로그인 성공한다`() {

        val signInRequestDto = SignInRequestDto(
            email = EXIST_EMAIL,
            password = EXIST_PASSWORD
        )

        mockMvc.post("/sign/v1/signIn") {
            content = objectMapper.writeValueAsString(signInRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.success") { value(true) } }
            .andExpect { jsonPath("$.code") { value(0) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }

    @Test
    fun `회원가입 시 이미 존재하는 계정이다`() {
        val signUpRequestDto = SignUpRequestDto(
            email = EXIST_EMAIL,
            password = EXIST_PASSWORD,
            nickname = EXIST_NICKNAME)

        mockMvc.post("/sign/v1/signUp") {
            content = objectMapper.writeValueAsString(signUpRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { is4xxClientError() } }
            .andExpect { jsonPath("$.success") { value(false) } }
            .andExpect { jsonPath("$.code") { value(-1005) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }

    @Test
    fun `로그인 시 존재하지 않는 회원이다`() {

        val signInRequestDto = SignInRequestDto(
            email = "blahblah@tistory.com",
            password = "1q2w3e4r!@#"
        )

        mockMvc.post("/sign/v1/signIn") {
            content = objectMapper.writeValueAsString(signInRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { is4xxClientError() } }
            .andExpect { jsonPath("$.success") { value(false) } }
            .andExpect { jsonPath("$.code") { value(-1000) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }

    @Test
    fun `로그인 시 패스워드가 일치하지 않는다`() {

        val signInRequestDto = SignInRequestDto(
            email = EXIST_EMAIL,
            password = "1q2w3e4r!@#"
        )

        mockMvc.post("/sign/v1/signIn") {
            content = objectMapper.writeValueAsString(signInRequestDto)
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { is4xxClientError() } }
            .andExpect { jsonPath("$.success") { value(false) } }
            .andExpect { jsonPath("$.code") { value(-1001) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }
}
