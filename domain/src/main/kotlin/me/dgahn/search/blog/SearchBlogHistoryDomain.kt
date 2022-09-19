package me.dgahn.search.blog

import java.time.Instant

data class SearchBlogHistoryDomain(
    val id: Long,
    val keyword: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    private val _searchCount: Int
) {
    var searchCount: Int = _searchCount
        protected set

    fun count() {
        searchCount += COUNT_PLUS_VALUE
    }

    companion object {
        private const val COUNT_PLUS_VALUE = 1
        private const val ID_DEFAULT_VALUE = 0L
        private const val SEARCH_COUNT_DEFAULT_VALUE = 0

        fun getDefault(keyword: String): SearchBlogHistoryDomain {
            return SearchBlogHistoryDomain(
                id = ID_DEFAULT_VALUE,
                keyword = keyword,
                createdAt = null,
                updatedAt = null,
                _searchCount = SEARCH_COUNT_DEFAULT_VALUE
            )
        }
    }
}
