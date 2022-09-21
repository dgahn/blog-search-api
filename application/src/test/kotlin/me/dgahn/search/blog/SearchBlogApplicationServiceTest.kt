package me.dgahn.search.blog

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.dgahn.client.BlogClient
import me.dgahn.client.kakao.KaKaoClient
import me.dgahn.client.kakao.KakaoBlogResponseListDto
import me.dgahn.client.naver.NaverClient
import me.dgahn.repository.SearchBlogHistoryJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SearchBlogApplicationServiceTest {

    @Autowired
    lateinit var searchBlogApplicationService: SearchBlogApplicationService

    @Autowired
    lateinit var searchBlogHistoryJpaRepository: SearchBlogHistoryJpaRepository

    @MockkBean
    lateinit var blogClient: BlogClient

    @MockkBean
    lateinit var kaKaoClient: KaKaoClient

    @MockkBean
    lateinit var naverClient: NaverClient

    @BeforeEach
    fun setUp() {
        every { blogClient.search(any()) } returns KakaoBlogResponseListDto(emptyList())

        searchBlogApplicationService.searchBlog(
            SearchBlogCondition(
                query = "keyword",
                sort = SearchBlogSort.RECENCY,
                page = 1,
                size = 10
            )
        )
    }

    @Test
    fun `블로그를_검색하면_검색_기록이_저장된다`() {
        val actual = searchBlogHistoryJpaRepository.findAllByOrderBySearchCountDesc(PageRequest.of(0, 10))
        actual.first().keyword shouldBe "keyword"
    }

    @Test
    fun `동시에_하나의_키워드로_검색하는_경우_검색_횟수를_정상적으로_카운트_할_수_있다`() = runBlocking {
        every { blogClient.search(any()) } returns KakaoBlogResponseListDto(emptyList())
        val testCount = 1000

        repeat(testCount) {
            CoroutineScope(Dispatchers.IO).launch {
                searchBlogApplicationService.searchBlog(
                    SearchBlogCondition(
                        query = "keyword",
                        sort = SearchBlogSort.RECENCY,
                        page = 1,
                        size = 10
                    )
                )
            }
        }
        delay(1000L)
        val actual = searchBlogHistoryJpaRepository.findAllByOrderBySearchCountDesc(PageRequest.of(0, 10))

        actual.first().searchCount shouldBe testCount + 1
    }
}
