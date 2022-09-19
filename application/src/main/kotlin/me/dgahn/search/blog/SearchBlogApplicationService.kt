package me.dgahn.search.blog

import me.dgahn.client.OutBoundSearchBlogCondition
import org.springframework.stereotype.Component

@Component
class SearchBlogApplicationService {
    // ToDo 삭제 필요
    @Suppress("UnusedPrivateMember", "EmptyFunctionBlock")
    fun searchBlog(searchBlogCondition: SearchBlogCondition) {
    }
}

// ToDo 추후에 분리 필요
data class SearchBlogCondition(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
) {
    fun toOutBoundCondition(): OutBoundSearchBlogCondition = OutBoundSearchBlogCondition(
        query = this.query,
        sort = this.sort,
        page = this.page,
        size = this.size
    )
}
