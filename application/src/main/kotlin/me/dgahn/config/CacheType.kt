package me.dgahn.config

@Suppress("MagicNumber")
enum class CacheType(
    val cacheName: String,
    val expiredAfterWrite: Long,
    val maximumSize: Long
) {
    SEARCHED_TOP("searchedTop", 5 * 60, 10000);
}
