package me.dgahn.search.blog

import me.dgahn.client.OutBoundSearchBlogCondition

data class SearchBlogCondition(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
) {
    fun toOutBoundCondition(): OutBoundSearchBlogCondition = OutBoundSearchBlogCondition(
        query = this.query,
        sort = this.sort,
        page = this.page,
        size = this.size
    )
}
