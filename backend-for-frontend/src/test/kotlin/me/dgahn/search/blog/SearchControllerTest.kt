package me.dgahn.search.blog

import me.dgahn.util.SpringMockMvcTestSupport
import me.dgahn.util.URI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebMvcTest
@Import(value = [SearchBlogApplicationService::class])
class SearchControllerTest : SpringMockMvcTestSupport() {

    @Test
    fun `블로그_목록을_검색할_수_있다`() {
        val uri = URI("/v1/search/blog")
        uri.addQueryParam("query", "검색어")
        uri.addQueryParam("sort", "accuracy")
        uri.addQueryParam("page", "1")
        uri.addQueryParam("size", "10")
        mockMvcGetTest(
            uri = uri,
            expectedResponseContent = ""
        )
    }
}
