package com.example.pepega.tenor.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class TenorControllerTest(
    @Autowired
    val mockMvc: MockMvc
) {

    @Test
    fun `tenor ImplementSearch를 호출한다`() {

        mockMvc.get("/tenor/v1") {
            contentType = MediaType.APPLICATION_JSON
            param("search", "excite")
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.success") { value(true) } }
            .andExpect { jsonPath("$.code") { value(0) } }
            .andExpect { jsonPath("$.message") { exists() } }
            .andExpect { jsonPath("$.results") { isArray() } }
            .andExpect { jsonPath("$.results.length()") { value(8) } }
    }
}
