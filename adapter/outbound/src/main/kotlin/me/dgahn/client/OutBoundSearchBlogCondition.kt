package me.dgahn.client

import kotlin.math.max

data class OutBoundSearchBlogCondition(
    val query: String,
    val sort: OutBoundSearchBlogSort,
    val page: Int,
    val size: Int
) {
    val start: Int = max(((page - 1) * size), 1)
    val display: Int = size
}

enum class OutBoundSearchBlogSort(
    val kakao: String,
    val naver: String,
) {
    ACCURACY(
        kakao = "accuracy",
        naver = "sim"
    ),
    RECENCY(
        kakao = "recency",
        naver = "date"
    )
}
