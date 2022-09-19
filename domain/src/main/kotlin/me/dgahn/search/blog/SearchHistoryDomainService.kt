package me.dgahn.search.blog

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SearchHistoryDomainService(
    private val searchHistoryDomainRepository: SearchBlogHistoryDomainRepository,
) {
    // outbound 계층을 아예 분리하려다보니 더티 체크를 사용하지 못함..
    @Async
    fun saveOrUpdate(keyword: String) {
        val searchBlogHistoryDomain = getByKeyword(keyword)
        searchBlogHistoryDomain.count()
        searchHistoryDomainRepository.saveOrUpdate(searchBlogHistoryDomain)
    }

    private fun getByKeyword(keyword: String): SearchBlogHistoryDomain {
        return searchHistoryDomainRepository.findByKeyword(keyword) ?: SearchBlogHistoryDomain.getDefault(keyword)
    }
}
