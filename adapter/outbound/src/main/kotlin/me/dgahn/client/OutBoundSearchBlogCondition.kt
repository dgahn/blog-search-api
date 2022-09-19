package me.dgahn.client

data class OutBoundSearchBlogCondition(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
)