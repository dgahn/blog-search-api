package me.dgahn.search.blog

import me.dgahn.client.BlogClient
import me.dgahn.search.blog.port.SearchBlogHistoryDomainRepository
import org.springframework.cache.annotation.Cacheable
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
    @Cacheable(cacheNames = ["searchedTop"])
    fun getSearchedTopHistory(): List<SearchBlogHistoryDomain> {
        return searchHistoryDomainRepository.findAllByOrderBySearchCount(SEARCHED_TOP_SIZE)
    }

    companion object {
        private const val SEARCHED_TOP_SIZE = 10
    }
}
