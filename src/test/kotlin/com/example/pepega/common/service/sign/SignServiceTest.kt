package com.example.pepega.common.service.sign

import com.example.pepega.common.dto.sign.request.SignUpRequestDto
import com.example.pepega.common.repository.UserMasterRepository
import org.junit.jupiter.api.Assertions.assertNotEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SignServiceTest(
    @Autowired
    val signService: SignService,

    @Autowired
    val userMasterRepository: UserMasterRepository
) {

    @Test
    fun `패스워드_암호화_여부`() {

        //given
        val signUpRequestDto = SignUpRequestDto(
            email = "pepega@pepetheflog.com",
            password = "3q4mf9ao8eirghj",
            nickname = "flog"
        )

        //when
        signService.signUp(signUpRequestDto)

        //then
        val findSignUpUser = userMasterRepository
            .findByEmail(signUpRequestDto.email)

        if (findSignUpUser != null) {
            assertNotEquals(signUpRequestDto.password, findSignUpUser.password)

            println("Input password : ${signUpRequestDto.password}, " +
                    "Output password : ${findSignUpUser.password}")
        }
    }
}
