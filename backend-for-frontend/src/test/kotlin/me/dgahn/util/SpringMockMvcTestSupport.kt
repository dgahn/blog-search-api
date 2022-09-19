package me.dgahn.util

import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
abstract class SpringMockMvcTestSupport {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected fun mockMvcGetTest(
        uri: URI,
        expectedResponseContent: String,
        customHeaders: HttpHeaders = HttpHeaders.EMPTY,
        status: HttpStatus = HttpStatus.OK
    ) {
        mockMvc.perform(
            MockMvcRequestBuilders.get(uri.value)
                .accept(MediaType.APPLICATION_JSON)
                .headers(customHeaders)
        )
            .andExpect { result -> result.response.status shouldBe status.value() }
            .andExpect(MockMvcResultMatchers.content().string(expectedResponseContent))
    }
}
