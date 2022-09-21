package me.dgahn.search.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.fixture.BlogDomainFixture
import me.dgahn.fixture.BlogResponseDtoFixture
import me.dgahn.fixture.SearchBlogHistoryDomainFixture
import me.dgahn.fixture.SearchBlogHistoryResponseDtoFixture
import me.dgahn.util.SpringMockMvcTestSupport
import me.dgahn.util.URI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebMvcTest
class BlogControllerTest(
    @Autowired private val objectMapper: ObjectMapper
) : SpringMockMvcTestSupport() {

    @MockkBean
    lateinit var searchBlogApplicationService: SearchBlogApplicationService

    @Test
    fun `블로그_목록을_검색할_수_있다`() {
        every { searchBlogApplicationService.searchBlog(any()) } returns BlogDomainFixture.getBlogDomains()
        val uri = URI("/v1/search/blog")
        uri.addQueryParam("query", "검색어")
        uri.addQueryParam("sort", "ACCURACY")
        uri.addQueryParam("page", "1")
        uri.addQueryParam("size", "10")
        val expected = BlogResponseDtoFixture.getBlogResponseDtos().toJson()
        mockMvcGetTest(
            uri = uri,
            expectedResponseContent = expected
        )
    }

    @Test
    fun `인기_검색어를_조회할_수_있다`() {
        every { searchBlogApplicationService.getSearchedTopHistory(any()) } returns SearchBlogHistoryDomainFixture
            .getSearchBlogHistoryDomains()
        val uri = URI("/v1/top-searched/blog")
        uri.addQueryParam("size", "10")
        val expected = SearchBlogHistoryResponseDtoFixture.getSearchBlogHistoryResponseDtos().toJson()
        mockMvcGetTest(
            uri = uri,
            expectedResponseContent = expected
        )
    }

    private inline fun <reified T> List<T>.toJson(): String = objectMapper.writeValueAsString(this)
}
