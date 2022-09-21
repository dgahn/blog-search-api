package me.dgahn.search.blog

import me.dgahn.client.OutBoundSearchBlogCondition
import me.dgahn.client.OutBoundSearchBlogSort

data class SearchBlogCondition(
    val query: String,
    val sort: SearchBlogSort,
    val page: Int,
    val size: Int
) {
    fun toOutBoundCondition(): OutBoundSearchBlogCondition = OutBoundSearchBlogCondition(
        query = this.query,
        sort = this.sort.toOutBound(),
        page = this.page,
        size = this.size
    )
}

enum class SearchBlogSort {
    ACCURACY,
    RECENCY;

    fun toOutBound(): OutBoundSearchBlogSort = when (this) {
        ACCURACY -> OutBoundSearchBlogSort.ACCURACY
        RECENCY -> OutBoundSearchBlogSort.RECENCY
    }
}
