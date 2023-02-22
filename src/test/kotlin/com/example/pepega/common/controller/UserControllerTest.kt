package com.example.pepega.common.controller

import com.example.pepega.common.domain.UserMaster
import com.example.pepega.common.dto.sign.request.SignInRequestDto
import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.repository.UserMasterRepository
import com.example.pepega.common.service.sign.SignService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest (
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val signService: SignService,

    @Autowired
    val userMasterRepository: UserMasterRepository,

    @Autowired
    val passwordEncoder: PasswordEncoder
) {

    companion object {
        private const val ID = "User"
        private const val ADMIN = "ADMIN@admin.com"
        private const val EMAIL = "@real.com"
        private const val PASSWORD = "existPassword!@#s2"
        private const val NICKNAME = "patric"
        private const val X_AUTH_TOKEN = "X-AUTH-TOKEN"
    }

    lateinit var token: String

    @BeforeEach
    fun setUp() {

        val roles = mutableListOf<String>()
        roles.add("ROLE_USER")
        roles.add("ROLE_ADMIN")

        userMasterRepository.save(
            UserMaster(
                email = ADMIN,
                password = passwordEncoder.encode(PASSWORD),
                nickName = NICKNAME,
                createUser = "setUpTestCode",
                updateUser = "setUpTestCode",
                roles = roles
            )
        )

        val signInRequestDto = SignInRequestDto(
            email = ADMIN,
            password = PASSWORD
        )

        token = signService.signIn(signInRequestDto).token

        for (i in 0..5) {
            val signUpRequestDto = SignUpRequestDto(
                email = ID + i + EMAIL,
                password = PASSWORD,
                nickname = NICKNAME
            )

            signService.signUp(signUpRequestDto)
        }
    }

    @Test
    fun `모든 유저 정보를 가져온다`() {

        mockMvc.get("/users/v1") {
            contentType = MediaType.APPLICATION_JSON
            header(X_AUTH_TOKEN, token)
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.success") { value(true) } }
            .andExpect { jsonPath("$.code") { value(0) } }
            .andExpect { jsonPath("$.message") { exists() } }
            .andExpect { jsonPath("$.results") { isNotEmpty() } }
    }
}