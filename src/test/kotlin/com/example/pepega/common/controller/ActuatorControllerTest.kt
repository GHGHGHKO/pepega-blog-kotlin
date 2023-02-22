package com.example.pepega.common.controller

import com.example.pepega.common.dto.sign.request.SignInRequestDto
import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.service.sign.SignService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ActuatorControllerTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val signService: SignService
) {

    companion object {
        private const val EXIST_EMAIL = "existUser@real.com"
        private const val EXIST_PASSWORD = "existPassword!@#s2"
        private const val EXIST_NICKNAME = "patric"
        private const val X_AUTH_TOKEN = "X-AUTH-TOKEN"
    }

    lateinit var token: String

    @BeforeEach
    fun setUp() {
        val signUpRequestDto = SignUpRequestDto(
            email = EXIST_EMAIL,
            password = EXIST_PASSWORD,
            nickname = EXIST_NICKNAME
        )

        val signInRequestDto = SignInRequestDto(
            email = EXIST_EMAIL,
            password = EXIST_PASSWORD
        )

        signService.signUp(signUpRequestDto)
        token = signService.signIn(signInRequestDto).token
    }

    @Test
    fun `actuator 호출 성공한다`() {

        mockMvc.get("/actuator") {
            header(X_AUTH_TOKEN, token)
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$._links") { exists() } }
    }

    @Test
    fun `토큰이 유효하지 않아서 오류가 발생한다`() {

        mockMvc.get("/actuator") {
            header(X_AUTH_TOKEN, token + "malfunction")
        }
            .andDo { print() }
            .andExpect { status { is4xxClientError() } }
            .andExpect { jsonPath("$.success") { value(false) } }
            .andExpect { jsonPath("$.code") { value(-1002) } }
            .andExpect { jsonPath("$.message") { exists() } }
    }
}
