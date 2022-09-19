package me.dgahn.search.blog

import java.time.Instant

data class SearchBlogHistoryDto(
    val id: Long,
    val keyword: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val searchCount: Int
)

fun SearchBlogHistoryDomain.toDto(): SearchBlogHistoryDto {
    return SearchBlogHistoryDto(
        id = id,
        keyword = keyword,
        createdAt = createdAt,
        updatedAt = updatedAt,
        searchCount = searchCount
    )
}

fun List<SearchBlogHistoryDomain>.toDto(): List<SearchBlogHistoryDto> = this.map { it.toDto() }
