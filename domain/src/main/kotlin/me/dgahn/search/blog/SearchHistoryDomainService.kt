package me.dgahn.search.blog

import me.dgahn.search.blog.port.SearchBlogHistoryDomainRepository
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SearchHistoryDomainService(
    private val searchHistoryDomainRepository: SearchBlogHistoryDomainRepository,
) {
    @Async
    @Retryable(maxAttempts = 2, backoff = Backoff(delay = 100))
    fun saveOrUpdate(keyword: String) {
        val searchBlogHistoryDomain = getByKeyword(keyword)
        searchBlogHistoryDomain.count()
        searchHistoryDomainRepository.saveOrUpdate(searchBlogHistoryDomain)
    }

    private fun getByKeyword(keyword: String): SearchBlogHistoryDomain {
        return searchHistoryDomainRepository.findByKeyword(keyword) ?: SearchBlogHistoryDomain.getDefault(keyword)
    }
}
