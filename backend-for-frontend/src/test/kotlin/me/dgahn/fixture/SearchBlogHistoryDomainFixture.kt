package me.dgahn.fixture

import me.dgahn.search.blog.SearchBlogHistoryDomain

object SearchBlogHistoryDomainFixture {
    fun getSearchBlogHistoryDomains(): List<SearchBlogHistoryDomain> = (10 downTo 1).map {
        SearchBlogHistoryDomain(
            id = it.toLong(),
            keyword = "keyword $it",
            createdAt = null,
            updatedAt = null,
            _searchCount = it,
        )
    }
}
