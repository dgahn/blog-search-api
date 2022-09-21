package me.dgahn.fixture

import me.dgahn.search.blog.SearchBlogHistoryResponseDto

object SearchBlogHistoryResponseDtoFixture {
    fun getSearchBlogHistoryResponseDtos(): List<SearchBlogHistoryResponseDto> = (10 downTo 1).map {
        SearchBlogHistoryResponseDto(
            id = it.toLong(),
            keyword = "keyword $it",
            createdAt = null,
            updatedAt = null,
            searchCount = it,
        )
    }
}
