package me.dgahn.client

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import me.dgahn.client.kakao.KaKaoClient
import me.dgahn.client.naver.NaverBlogResponseListDto
import me.dgahn.client.naver.NaverClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class BaseBlogClientTest {

    @MockkBean
    lateinit var kaKaoClient: KaKaoClient

    @MockkBean
    lateinit var naverClient: NaverClient

    @Autowired
    lateinit var blogClient: BlogClient

    @Test
    fun `KaKaoClient에_에러가_발생하면_NaverClient로_요청할_수_있다`() {
        val condition = OutBoundSearchBlogCondition(
            query = "keyword",
            sort = OutBoundSearchBlogSort.ACCURACY,
            page = 1,
            size = 10
        )
        every { kaKaoClient.search(condition) } throws RuntimeException()
        every { naverClient.search(condition) } returns NaverBlogResponseListDto(
            lastBuildDate = "",
            total = 0L,
            start = 0L,
            display = 0,
            emptyList()
        )

        blogClient.search(condition)
        verify { naverClient.search(condition) }
    }
}
