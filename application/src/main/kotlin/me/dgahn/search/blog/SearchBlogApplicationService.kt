package me.dgahn.search.blog

import me.dgahn.client.BlogClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SearchBlogApplicationService(
    private val blogClient: BlogClient,
    private val searchHistoryDomainRepository: SearchBlogHistoryDomainRepository,
) {
    @Transactional
    fun searchBlog(searchBlogCondition: SearchBlogCondition): List<BlogDomain> {
        val searchBlogHistoryDomain = getByKeyword(searchBlogCondition.query)
        searchHistoryDomainRepository.saveOrUpdate(searchBlogHistoryDomain)
        return blogClient.search(searchBlogCondition.toOutBoundCondition()).toDomain()
    }

    private fun getByKeyword(keyword: String): SearchBlogHistoryDomain {
        return searchHistoryDomainRepository.findByKeyword(keyword) ?: SearchBlogHistoryDomain.getDefault(keyword)
    }
}
