package me.dgahn.search.blog

import me.dgahn.client.BlogClient
import org.springframework.stereotype.Component

@Component
class SearchBlogApplicationService(
    private val blogClient: BlogClient
) {
    fun searchBlog(searchBlogCondition: SearchBlogCondition): List<BlogDomain> {
        return blogClient.search(searchBlogCondition.toOutBoundCondition()).toDomain()
    }
}
