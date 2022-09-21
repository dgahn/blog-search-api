package me.dgahn.search.blog

import java.time.Instant

data class SearchBlogHistoryResponseDto(
    val id: Long,
    val keyword: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val searchCount: Int
)

fun SearchBlogHistoryDomain.toDto(): SearchBlogHistoryResponseDto {
    return SearchBlogHistoryResponseDto(
        id = id,
        keyword = keyword,
        createdAt = createdAt,
        updatedAt = updatedAt,
        searchCount = searchCount
    )
}

fun List<SearchBlogHistoryDomain>.toDto(): List<SearchBlogHistoryResponseDto> = this.map { it.toDto() }
