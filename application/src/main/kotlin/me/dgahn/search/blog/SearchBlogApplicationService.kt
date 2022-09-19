package me.dgahn.search.blog

import me.dgahn.client.BlogClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SearchBlogApplicationService(
    private val blogClient: BlogClient,
    private val searchHistoryDomainService: SearchHistoryDomainService,
    private val searchHistoryDomainRepository: SearchBlogHistoryDomainRepository,
) {
    @Transactional
    fun searchBlog(searchBlogCondition: SearchBlogCondition): List<BlogDomain> {
        searchHistoryDomainService.saveOrUpdate(searchBlogCondition.query)
        return blogClient.search(searchBlogCondition.toOutBoundCondition()).toDomain()
    }

    @Transactional(readOnly = true)
    fun getSearchedTopHistory(size: Int): List<SearchBlogHistoryDomain> {
        return searchHistoryDomainRepository.findAllByOrderBySearchCount(size)
    }
}
